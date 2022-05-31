package com.example.practice7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {

    private static final int NONE = 0;
    private static final int TRIANGLE = 1;
    private static final int CIRCLE = 2;
    private static final int RECTANGLE = 3;
    private static final int RAIDE = 4;
    private static final int CUSTOM = 5;

    public boolean fillFlag = false;
    private int figure;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DrawView(Context context, AttributeSet attributeSet,
                    int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public boolean getFillFlag() {
        return fillFlag;
    }

    public void setFillFlag(boolean fillFlag) {
        this.fillFlag = fillFlag;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int heigth = getHeight();
        Paint paint;

        switch (figure) {
            case TRIANGLE: {

                paint = new Paint();
                paint.setColor(Color.GREEN);

                if (fillFlag) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                }

                paint.setStrokeWidth(10f);

                Point point1_draw = new Point(width / 2, 0);
                Point point2_draw = new Point(0, heigth);
                Point point3_draw = new Point(width, heigth);

                Path path = new Path();
                path.moveTo(point1_draw.x, point1_draw.y);
                path.lineTo(point2_draw.x, point2_draw.y);
                path.lineTo(point3_draw.x, point3_draw.y);
                path.lineTo(point1_draw.x, point1_draw.y);
                path.close();
                canvas.drawPath(path, paint);
                break;

            }
            case CIRCLE: {

                paint = new Paint();
                paint.setColor(Color.BLUE);
                if (fillFlag) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                }
                paint.setStrokeWidth(10f);

                canvas.drawCircle(width / 2, heigth / 2, width / 2, paint);
                break;

            }
            case RECTANGLE: {

                paint = new Paint();
                paint.setColor(Color.BLACK);
                if (fillFlag) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                }
                paint.setStrokeWidth(10f);
                canvas.drawRect(0, 0, width, heigth, paint);
                break;

            }
            case RAIDE: {

                paint = new Paint();
                paint.setColor(getResources().getColor(R.color.purple_200));

                if (fillFlag) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                }

                paint.setStrokeWidth(10f);

                Point point1_draw = new Point(2, 10);
                Point point2_draw = new Point(width * 5 / 12, heigth * 2 / 14);
                Point point3_draw = new Point(width * 9 / 12, heigth * 5 / 14);
                Point point4_draw = new Point(width * 10 / 12, heigth * 9 / 14);
                Point point5_draw = new Point(width * 9 / 12, heigth * 12 / 14);
                Point point6_draw = new Point(width * 5 / 12, heigth * 27 / 28);
                Point point7_draw = new Point(2, heigth - 10);

                Path path = new Path();

//                path.moveTo(point1_draw.x, point1_draw.y); // pirmas variantas kampuotas
//                path.lineTo(point2_draw.x, point2_draw.y);
//                path.lineTo(point3_draw.x, point3_draw.y);
//                path.lineTo(point4_draw.x, point4_draw.y);
//                path.lineTo(point5_draw.x, point5_draw.y);
//                path.lineTo(point6_draw.x, point6_draw.y);
//                path.lineTo(point7_draw.x, point7_draw.y);
//                path.lineTo(point1_draw.x, point1_draw.y);

                // RectF(float left, float top, float right, float bottom) // antras variantas lenktas
                path.moveTo(point1_draw.x + 314, point1_draw.y);
                path.arcTo(new RectF(point1_draw.x, point1_draw.y, point4_draw.x, point4_draw.y), -90, 90);
                path.arcTo(new RectF(point1_draw.x, point1_draw.y, point5_draw.x + 79, point5_draw.y - 200), 0, 100);
                path.lineTo(point1_draw.x + 314, point1_draw.y);

                path.close();
                canvas.drawPath(path, paint);
                break;

            }
            case CUSTOM: {

            }

            case NONE: {
                break;
            }
            default:
                break;
        }
    }

}
