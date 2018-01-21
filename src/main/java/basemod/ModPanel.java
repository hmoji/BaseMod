package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ModPanel {
    private static final float ROW_LEFT = 475.0f;
    private static final float ROW_TOP = 650.0f;
    private static final float ROW_HEIGHT = 64.0f;
    
    private static Texture background;
    private ArrayList<ModButton> buttons;
    private ArrayList<ModLabel> labels;
    
    public InputProcessor oldInputProcessor = null;
    public boolean isUp = false;
    public boolean waitingOnEvent = false;
    
    public ModPanel() {
        background = new Texture(Gdx.files.internal("img/ModPanelBg.png"));
        buttons = new ArrayList<ModButton>();
        labels = new ArrayList<ModLabel>();
    }
    
    public void addButton(float x, float y, Consumer<ModButton> click) {
        buttons.add(new ModButton(x, y, this, click));
    }
    
    public void addLabel(String text, float x, float y, Consumer<ModLabel> update) {
        labels.add(new ModLabel(text, x, y, this, update));
    }
    
    public void render(SpriteBatch sb) {
        renderBg(sb);
        renderText(sb);
        renderButtons(sb);
    }
    
    private void renderBg(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(background, (float)Settings.WIDTH / 2.0f - 682.0f, Settings.OPTION_Y - 376.0f, 682.0f, 376.0f, 1364.0f, 752.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1364, 752, false, false);
    }
    
    private void renderText(SpriteBatch sb) {
        for (ModLabel label : labels) {
            label.render(sb);
        }
    }
    
    private void renderButtons(SpriteBatch sb) {
        for (ModButton button : buttons) {
            button.render(sb);
        }
    }
    
    public void update() {
        updateText();
        updateButtons();
        
        if (InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            BaseMod.modSettingsUp = false;
        }
        
        if (!BaseMod.modSettingsUp) {
            waitingOnEvent = false;
            Gdx.input.setInputProcessor(oldInputProcessor);
            CardCrawlGame.mainMenuScreen.lighten();
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.cancelButton.hideInstantly();
            isUp = false;
        }
    }
    
    private void updateText() {
        for (ModLabel label : labels) {
            label.update();
        }
    }
    
    private void updateButtons() {
        for (ModButton button : buttons) {
            button.update();
        }
    }
}