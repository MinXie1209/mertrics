package minxie.space.thread.metrics

import minxie.space.metrics.core.thread.ThreadPoolContext
import minxie.space.metrics.vo.MetricBaseVo
import minxie.space.metrics.vo.MetricItemVo
import java.util.concurrent.ThreadPoolExecutor

private const val THREAD_POOL_NAME = "threadPoolName"
private const val ROCKETMQ_THREAD_POOL_ACTIVE_COUNT = "rocketmq_thread_pool_active_count"

private const val ROCKETMQ_THREAD_POOL_CORE_POOL_SIZE = "rocketmq_thread_pool_core_pool_size"

private const val ROCKETMQ_THREAD_POOL_MAXIMUM_POOL_SIZE = "rocketmq_thread_pool_maximum_pool_size"

private const val ROCKETMQ_THREAD_POOL_QUEUE_SIZE = "rocketmq_thread_pool_queue_size"

private const val ROCKETMQ_THREAD_POOL_COMPLETED_TASK_COUNT = "rocketmq_thread_pool_completed_task_count"

private const val HASH_CODE = "hashCode"

class RocketMQThreadPoolMetricsVo : MetricBaseVo() {
    init {
        ThreadPoolContext.getRocketMQThreadPoolMap().filter { it.key is ThreadPoolExecutor }
            .forEach { (it, threadPoolName) ->
                it as ThreadPoolExecutor
                val tag = mapOf(THREAD_POOL_NAME to threadPoolName, HASH_CODE to it.hashCode().toString())
                it.activeCount.let { activeCount ->
                    metricList.add(MetricItemVo(ROCKETMQ_THREAD_POOL_ACTIVE_COUNT, activeCount.toFloat(), tag))
                }

                it.corePoolSize.let { corePoolSize ->
                    metricList.add(MetricItemVo(ROCKETMQ_THREAD_POOL_CORE_POOL_SIZE, corePoolSize.toFloat(), tag))
                }

                it.maximumPoolSize.let { maximumPoolSize ->
                    metricList.add(MetricItemVo(ROCKETMQ_THREAD_POOL_MAXIMUM_POOL_SIZE, maximumPoolSize.toFloat(), tag))
                }

                it.queue?.let { queue ->
                    metricList.add(MetricItemVo(ROCKETMQ_THREAD_POOL_QUEUE_SIZE, queue.size.toFloat(), tag))
                }

                it.completedTaskCount.let { completedTaskCount ->
                    metricList.add(
                        MetricItemVo(
                            ROCKETMQ_THREAD_POOL_COMPLETED_TASK_COUNT,
                            completedTaskCount.toFloat(),
                            tag
                        )
                    )
                }
            }
    }
}