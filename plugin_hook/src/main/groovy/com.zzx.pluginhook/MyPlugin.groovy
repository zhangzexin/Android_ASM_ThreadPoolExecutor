package com.zzx.pluginhook;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;

import org.gradle.BuildListener;
import org.gradle.BuildResult;
import org.gradle.api.Plugin;
import org.gradle.api.Project
import org.gradle.api.Task;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;

class MyPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

//        project.getGradle().addBuildListener(new BuildListener() {
//
//            @Override
//            public void beforeSettings(Settings settings) {
//                BuildListener.super.beforeSettings(settings);
//            }
//
//
//            @Override
//            public void settingsEvaluated(Settings settings) {
//                System.out.println("分析setting.gradle");
//            }
//
//            @Override
//            public void projectsLoaded(Gradle gradle) {
//                System.out.println("gradle projectsLoaded");
//
//            }
//
//            @Override
//            public void projectsEvaluated(Gradle gradle) {
//                System.out.println("gradle projectsEvaluated");
//            }
//
//            @Override
//            public void buildFinished(BuildResult buildResult) {
//                System.out.println("gradle 结束了");
//            }
//        });
//        System.out.println("开始编写gradle插件");
        registerForApp(project)
//        AppExtension byType = project.getExtensions().findByType(AppExtension.class);
//        if (byType != null) {
//            byType.registerTransform(new TestTransform(project));
//        }

    }

    /**
     * 注册 for android 工程
     */
    static void registerForApp(Project project) {
        AppExtension appExtension = project.extensions.getByType(AppExtension.class)
        def PluginExt = project.getExtensions().create("PluginExt",  PluginExt.class)
        appExtension.registerTransform(new ClassTransform(PluginExt))
    }
}
