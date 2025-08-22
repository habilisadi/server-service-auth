package com.habilisadi.auth.infrastructure.config

import com.habilisadi.file.PendingServiceGrpc
import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.EurekaClient
import io.grpc.Channel
import io.grpc.ManagedChannel
import io.grpc.netty.NettyChannelBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.grpc.client.GrpcChannelFactory
import java.util.concurrent.TimeUnit


@Configuration
class GrpcConfig(
    private val client: EurekaClient,
) {

    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    @Bean
    fun pendingServiceBlockingStub(@Qualifier("fileChannel") channel: Channel): PendingServiceGrpc.PendingServiceBlockingStub {
        return PendingServiceGrpc.newBlockingStub(channel)
    }

    @Bean
    fun fileChannel(grpcChannelFactory: GrpcChannelFactory): Channel {
        return createChannelFromEureka("file")
    }

    private fun createChannelFromEureka(serviceName: String): ManagedChannel {
        val instance: InstanceInfo = client.getNextServerFromEureka(serviceName, true)
            ?: throw RuntimeException("Service not found: $serviceName")


        val grpcPort = instance.metadata["grpc.port"]
            ?: throw RuntimeException("gRPC port not found in service metadata")

        val target = instance.ipAddr + ":" + grpcPort

        log.info("Creating gRPC channel to: $target")

        return NettyChannelBuilder.forTarget(target)
            .usePlaintext()
            .keepAliveTime(30, TimeUnit.SECONDS)
            .keepAliveTimeout(5, TimeUnit.SECONDS)
            .keepAliveWithoutCalls(true)
            .build()
    }

}