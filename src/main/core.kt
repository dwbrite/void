package main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.toInternalFile
import ktx.async.enableKtxCoroutines
import ktx.inject.Context
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.style.*


class Application : KtxGame<Screen>() {
    val context = Context()

    override fun create() {
        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        context.register {
             bindSingleton(TextureAtlas("assets/skin.atlas"))
             bindSingleton<Batch>(SpriteBatch())
             bindSingleton<Viewport>(FitViewport(256f, 144f))
             bindSingleton(Stage(inject(), inject()))
             bindSingleton(createSkin(inject()))
             Scene2DSkin.defaultSkin = inject()
             bindSingleton(this@Application)
             bindSingleton(Menu(inject(), inject()))
        }

        playMusic()

        addScreen(context.inject<Menu>())
        setScreen<Menu>()
    }

    private fun playMusic() {
        Gdx.audio.newMusic("res/lowtide.ogg".toInternalFile()).apply {
            volume = 1f
            //rate = 0.5
            setOnCompletionListener { play() }
        }.play()
    }

    fun createSkin(atlas: TextureAtlas): Skin = skin(atlas) { skin ->
        add(defaultStyle, BitmapFont())
        add("text", FreeTypeFontGenerator("res/PressStart2P.ttf".toInternalFile())
            .generateFont(FreeTypeFontParameter().apply {
                borderWidth = 0f
                borderColor = Color.WHITE
                size = 8
            }))
        label {
            font = skin["text"]
        }
        label("text") {
            font = skin["text"]
        }
        textButton("text") {
            font = skin["text"]
            overFontColor = Color.GRAY
            downFontColor = Color.DARK_GRAY
        }
        window {
            titleFont = skin["text"]
            stageBackground = skin["menu"]
        }
    }

    override fun dispose() {
        context.dispose()
    }
}

class Menu(val stage: Stage, val application: Application) : KtxScreen {
    val backgroundImage = Texture("res/menu.png")

    var bgAnimStateTime = 0f

    val a = Animation<TextureRegion>(0.20000012f,
        TextureRegion(backgroundImage, 0, 0, 256, 144),
        TextureRegion(backgroundImage, 256, 0, 256, 144),
        TextureRegion(backgroundImage, 512, 0, 256, 144),
        TextureRegion(backgroundImage, 768, 0, 256, 144) )

    val view = table {
        setDebug(true)
        setFillParent(true)

        background = TextureRegionDrawable(a.getKeyFrame(0f))
        //touchable = enabled
        //onClick { _, _ -> application.setScreen<Game>() }

/*        label(text = "click to move").cell(row = true, padBottom = 0.1f, padLeft = 0.1f)
        label(text = "point to aim").cell(row = true, padBottom = 0.1f, padLeft = 0.1f)
        label(text = "QWER to cast").cell(row = true, padBottom = 0.1f, padLeft = 0.1f)*/
        label(text = "Click to play")
    }

    override fun show() {
        stage.addActor(view)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        bgAnimStateTime += delta

        //bgAnimStateTime %= a.animationDuration

        if (a.isAnimationFinished(bgAnimStateTime))
             bgAnimStateTime -= a.animationDuration

        view.background = TextureRegionDrawable(a.getKeyFrame(bgAnimStateTime))




        stage.act(delta)
        stage.draw()
    }

    override fun hide() {
        view.remove()
    }
}