# 目录结构
```bash
|-tesapp 测试工程
|-app 测试工程
|-plugin_hook Gradle插件源文件
|      |-main
|          |-groovy 存放Gradle脚本
|          |-java 对应的转换类，实际操控ASM
|-repo 生成好的插件
```
## 使用方法
项目下build.gradle引入

```

    repositories {
        maven {
                url uri('repo')
            }
    }
    dependencies {
        classpath 'com.zzx.pluginhook:pluginhook:1.0'
    }

```

主工程模块下

```

    plugins {
        id 'com.zzx.pluginhook'
    }
    PluginExt {
        poolmethod = "asw_getInstance"
        threadpool_des = "Lcom/zzx/testapp/proxy/JavaThreadPoolExecutorProxy;" //需要替换成的ThreadPoolExecutor类
        thread_des = "Lcom/zzx/testapp/proxy/ThreadProxy;" //需要代理替换的Thread
    }

```
## 需要关注的问题
1. 替换时会发现官方所提供的类也进行了替换，这时候看个人处理，比如DelegatedExecutorService的类在livedata中使用，看自己否需要替换
2. 如果感觉编译过慢，可以引入[ByteX](https://github.com/bytedance/ByteX)将对应的adapter添加进去即可。
3. 因为各家引包不同，需要做一定的处理，如果不想处理建议增加过滤措施。

## 后续计划
- [x] 添加线程池收敛方案
- [ ] 添加过滤措施
- [ ] 将ByteX的部分引入，进行重构成，目前bytex感觉还是过于复杂，对其他人进行扩展不太友好，最友好的方案是，只需要添加一个task的脚本中选择操作对应的字节码就好了。
