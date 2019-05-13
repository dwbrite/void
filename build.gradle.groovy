plugins {
    id 'java' // this probably isn't necessary
    id 'idea'
    id 'application'
    id 'org.jetbrains.kotlin.jvm' version '1.3.31'
    id "com.github.johnrengelman.shadow" version "2.0.2"
}

group 'o_hej'
version '0.1.0'

sourceSets {
    main.kotlin.srcDirs += 'src'
    main.resources.srcDirs += 'dep'
}

sourceCompatibility = 1.8
mainClassName = "main.Main"

repositories {
    mavenCentral()
    jcenter()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    maven { url "https://oss.sonatype.org/content/repositories/releases/" }
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    compile "org.jetbrains.kotlin:kotlin-reflect"

    compile group: 'io.github.libktx', name: 'ktx-actors', version: '1.9.9-b1'
    compile "com.badlogicgames.gdx:gdx:$gdxVersion"
    compile "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"
    compile "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"

    compile "com.badlogicgames.gdx:gdx-freetype:$gdxVersion"

    compile "io.github.libktx:ktx-log:$ktxLogVersion"
    compile "io.github.libktx:ktx-scene2d:$ktxScene2DVersion"
    compile "io.github.libktx:ktx-async:$ktxAsyncVersion"
    compile "io.github.libktx:ktx-box2d:$ktxBox2dVersion"
    compile "io.github.libktx:ktx-inject:$ktxInjectVersion"
    compile "io.github.libktx:ktx-app:$ktxAppVersion"
    compile "io.github.libktx:ktx-math:$ktxMathVersion"
    compile "io.github.libktx:ktx-assets:$ktxAssetsVersion"
    compile "io.github.libktx:ktx-i18n:$ktxI18nVersion"
    compile "io.github.libktx:ktx-actors:$ktxActorsVersion"
    compile "io.github.libktx:ktx-collections:$ktxCollectionsVersion"
    compile "io.github.libktx:ktx-style:$ktxStyleVersion"

}

jar {
    manifest {
        attributes 'Main-Class': 'main.Main'
    }
}

idea.module { excludeDirs += file('target/')}

task gameZip(type: Zip) {
    shadowJar

    from '.'
    include 'native/**'
    include 'res/**'
    
    from 'build/libs'
    include 'void2*all.jar'

    archiveName 'void2.zip'
    destinationDir(file('target/'))
}

task pack() {
    /*TexturePacker.process(
            'res', // Raw assets path.
            'assets', // Output directory.
            'skin' // Name of the generated atlas (without extension).
    )*/
}