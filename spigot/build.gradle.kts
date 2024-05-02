dependencies {
    implementation(project(":common"))
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper.packetevents:spigot:2.2.1")
}

tasks {
    runServer {
        // The version of the server to run
        val version = "1.20.6"

        minecraftVersion(version)
        runDirectory.set(file("server/$version"))

        // 1.8.8 - 1.16.5 = Java 8
        // 1.17           = Java 16
        // 1.18 - 1.20.4  = Java 17
        // 1-20.5+        = Java 21
        javaLauncher.set(project.javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(21))
        })

        downloadPlugins {
            url("https://ci.codemc.io/job/retrooper/job/packetevents/lastBuild/artifact/spigot/build/libs/packetevents-spigot-2.2.1.jar")
            url("https://ci.lucko.me/job/spark/400/artifact/spark-bukkit/build/libs/spark-1.10.59-bukkit.jar")
            url("https://download.luckperms.net/1530/bukkit/loader/LuckPerms-Bukkit-5.4.117.jar")
            url("https://github.com/ViaVersion/ViaVersion/releases/download/4.10.0/ViaVersion-4.10.0.jar")
            url("https://github.com/ViaVersion/ViaBackwards/releases/download/4.10.0/ViaBackwards-4.10.0.jar")
        }

        jvmArgs = listOf(
            "-Dcom.mojang.eula.agree=true"
        )
    }
}