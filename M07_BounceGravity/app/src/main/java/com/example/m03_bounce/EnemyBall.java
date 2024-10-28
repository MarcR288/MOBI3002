package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class EnemyBall extends Ball{

    private int health;
    Random r = new Random();
    BouncingBallView bbView;
    private Paint paint;
    private static int scoreCount = 0;
    private boolean enemyBallAddedatCurrentScore;

    public EnemyBall(int color, double x, double y, int health, String name, BouncingBallView bbView) {
        super(color, (float) x, (float) y, 0, 0, true, "Enemy"); // Call the Ball constructor
        this.health = health; // Initialize the health property
        this.bbView = bbView;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(getColorBasedonHealth());
        super.paint = paint;
        super.draw(canvas); // Call the Ball's draw method
        // Additional drawing logic for enemy ball can be added here, if needed
        paint.setColor(getColorBasedonHealth());
    }

    public int getHealth(){
        return health;
    }

    public void reduceHealth(int amount) {
        health -= amount;
        if (health <= 0){
            enemyDie();
        }
    }

    @Override
    public double getRadius() {
        return super.getRadius();
    }

    public void enemyDie() {
            incrementScore();
            if (scoreCount > 0 && scoreCount % 5 == 0) {
                addBallEveryFive();
            }
            Log.v("scoreCount", "Current Score: " + scoreCount);
            if (scoreCount % 10 == 0){
                bbView.enablePowerButton();
            }
            respawn();
    }

    private void respawn() {
        int canvasWidth = bbView.getWidth();
        int canvasHeight = bbView.getHeight();
        double newX = r.nextInt((int) (canvasWidth - 2 * getRadius())) + getRadius();
        double newY = r.nextInt((int) (canvasHeight - 2 * getRadius())) + getRadius();
        this.x = newX;
        this.y = newY;
        setHealth(2);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private int getColorBasedonHealth(){
        if (health == 2) {
            return Color.MAGENTA;
        } else if (health == 1) {
            return Color.RED;
        }
        return Color.MAGENTA;
    }

    public static int getScoreCount() {
        return scoreCount;
    }

    public void addBallEveryFive(){
        if (!enemyBallAddedatCurrentScore) {
                bbView.addEnemyBall();
                enemyBallAddedatCurrentScore = true;
        }
        else {
            enemyBallAddedatCurrentScore = false;
        }
    }

    public synchronized void incrementScore(){
        scoreCount++;
    }



}
