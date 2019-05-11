package main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.app.KtxGame
import ktx.assets.toInternalFile
import ktx.async.enableKtxCoroutines
import ktx.inject.Context
import ktx.style.*


class Application : KtxGame<Screen>() {
    val context = Context()

    override fun create() {
        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        context.register {
/*             bindSingleton(TextureAtlas("skin.atlas"))
             bindSingleton<Batch>(SpriteBatch())
             bindSingleton<Viewport>(ScreenViewport())
             bindSingleton(Stage(inject(), inject()))
             bindSingleton(createSkin(inject()))
             Scene2DSkin.defaultSkin = inject()
             bindSingleton(this@Application)
             bindSingleton(Menu(inject(), inject()))
             bindSingleton(Game(inject(), inject()))*/
        }

        playMusic()
    }

    private fun playMusic() {
        Gdx.audio.newMusic("res/lowtide.ogg".toInternalFile()).apply {
            volume = 1f
            //rate = 0.5
            setOnCompletionListener { play() }
        }.play()
    }

    fun createSkin(atlas: TextureAtlas): Skin = skin(atlas) {
            /*skin ->
        add(defaultStyle, BitmapFont())
        add("decorative", FreeTypeFontGenerator("decorative.ttf".toInternalFile())
            .generateFont(FreeTypeFontParameter().apply {
                borderWidth = 2f
                borderColor = Color.GRAY
                size = 50
            }))
        label {
            font = skin[defaultStyle]
        }
        label("decorative") {
            font = skin["decorative"]
        }
        textButton("decorative") {
            font = skin["decorative"]
            overFontColor = Color.GRAY
            downFontColor = Color.DARK_GRAY
        }
        window {
            titleFont = skin[defaultStyle]
            stageBackground = skin["black-alpha"]
        }
        */
    }

    override fun dispose() {
        context.dispose()
    }
}