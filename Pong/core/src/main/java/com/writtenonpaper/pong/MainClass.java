package com.writtenonpaper.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainClass extends ApplicationAdapter {

    // Initiate variables
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;

    BitmapFont font;

    int gameWidth, gameHeight;

    int player1Score, player2Score;

    float playerRectWidth, playerRectHeight;
    float ballRectWidth, ballRectHeight;

    int moveSpeed;

    Rectangle player1Rect, player2Rect;
    Rectangle ballRect;

    float ballVelocityX;
    float ballVelocityY;
    float ballMoveSpeed;


    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        font = new BitmapFont();

        gameWidth = 320;
        gameHeight = 240;

        player1Score = 0;
        player2Score = 0;

        playerRectWidth = 2;
        playerRectHeight =  40;
        ballRectWidth = 8;
        ballRectHeight = 8;

        moveSpeed = 250;

        player1Rect = new Rectangle(0, (gameHeight / 2) - (playerRectWidth / 2), playerRectWidth, playerRectHeight);
        player2Rect = new Rectangle(gameWidth - playerRectWidth, (gameHeight / 2) - (playerRectWidth / 2), playerRectWidth, playerRectHeight);
        ballRect = new Rectangle((gameWidth / 2) - (ballRectWidth / 2), (gameHeight / 2) - (ballRectHeight / 2), ballRectWidth, ballRectHeight);

        ballVelocityX = 1;
        ballVelocityY = 1;
        ballMoveSpeed = 100;

    }

    private void controlPlayer(float delta, int upKey, int downKey, Rectangle rect) {
        boolean moveUp = Gdx.input.isKeyPressed(upKey);
        boolean moveDown = Gdx.input.isKeyPressed(downKey);

        if (moveUp && rect.y < gameHeight - rect.getHeight()) {rect.y += moveSpeed * delta;}
        if (moveDown && rect.y > 0) {rect.y -= moveSpeed * delta;}
    }


    private void respawnBall() {
        ballRect.x = (gameWidth / 2) - (ballRectWidth / 2);
        ballRect.y = (gameHeight / 2) - (ballRectHeight / 2);
    }


    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        float delta = Gdx.graphics.getDeltaTime();

        controlPlayer(delta, Input.Keys.W, Input.Keys.S, player1Rect);
        controlPlayer(delta, Input.Keys.UP, Input.Keys.DOWN, player2Rect);

        ballRect.x += ballVelocityX * (ballMoveSpeed * delta);
        ballRect.y += ballVelocityY * (ballMoveSpeed * delta);

        if (ballRect.overlaps(player1Rect) || ballRect.overlaps(player2Rect)) {
            ballVelocityX *= -1;


        }

        if (ballRect.getY() > 240 - ballRect.getHeight() || ballRect.getY() < 0) {
            ballVelocityY *= -1;
        }

        if (ballRect.getX() > 320) {
            player1Score++;
            respawnBall();
        }
        else if (ballRect.getX() < 0 - ballRect.getWidth()) {
            player2Score++;
            respawnBall();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.line(320 / 2, 0, 320 / 2, 240);

        shapeRenderer.rect(player1Rect.getX(), player1Rect.getY(), player1Rect.getWidth(), player1Rect.getHeight());
        shapeRenderer.rect(player2Rect.getX(), player2Rect.getY(), player2Rect.getWidth(), player2Rect.getHeight());
        shapeRenderer.rect(ballRect.getX(), ballRect.getY(), ballRect.getWidth(), ballRect.getHeight());

        shapeRenderer.end();


        spriteBatch.begin();

        font.setColor(Color.WHITE);
        font.draw(spriteBatch, Integer.toString(player1Score), 0, gameHeight);
        font.draw(spriteBatch, Integer.toString(player2Score), 0, gameHeight - 16);

        spriteBatch.end();


    }

    @Override
    public void dispose() {

    }
}
