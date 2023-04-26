//package com.zzx.testapp.proxy
//
//import java.util.concurrent.*
//
//
//class ThreadPoolExecutorProxy : ThreadPoolExecutor {
//
//    constructor( corePoolSize: Int,
//                 maximumPoolSize: Int,
//                 keepAliveTime: Long,
//                 unit: TimeUnit?,
//                 workQueue: BlockingQueue<Runnable?>?):super( corePoolSize, maximumPoolSize,keepAliveTime,unit,workQueue)
//    constructor(
//        corePoolSize: Int,
//        maximumPoolSize: Int,
//        keepAliveTime: Long,
//        unit: TimeUnit?,
//        workQueue: BlockingQueue<Runnable?>?,
//        threadFactory: ThreadFactory?):super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory)
//
//    constructor(corePoolSize: Int,
//                maximumPoolSize: Int,
//                keepAliveTime: Long,
//                unit: TimeUnit?,
//                workQueue: BlockingQueue<Runnable?>?,
//                handler: RejectedExecutionHandler?): super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler)
//
//    constructor(corePoolSize: Int,
//                maximumPoolSize: Int,
//                keepAliveTime: Long,
//                unit: TimeUnit?,
//                workQueue: BlockingQueue<Runnable?>?,
//                threadFactory: ThreadFactory?,
//                handler: RejectedExecutionHandler?):super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler)
//
//
//
//    companion object {
//        @JvmField
//        val instance: ThreadPoolExecutorProxy = ThreadPoolExecutorProxy(
//            0, Runtime.getRuntime().availableProcessors() * 3,
//            30L, TimeUnit.SECONDS,
//            LinkedBlockingQueue()
//        )
//        @JvmStatic
//        fun getInstance(): ThreadPoolExecutorProxy {
//            return instance
//        }
//    }
//
////    fun getInstance(): BaseThreadPoolExecutor? {
////        return CoreThreadPoolExecutor.INSTANCE
////    }
////
////    private object CoreThreadPoolExecutor {
////        val INSTANCE: BaseThreadPoolExecutor = ThreadPoo2(
////            0, Runtime.getRuntime().availableProcessors() * 3,
////            30L, TimeUnit.SECONDS,
////            LinkedBlockingQueue()
////        )
////    }
//
//
//    override fun execute(command: Runnable?) {
//        getInstance().execute(command)
//    }
//
//    override fun afterExecute(r: Runnable?, t: Throwable?) {
//        super.afterExecute(r, t)
//    }
//}