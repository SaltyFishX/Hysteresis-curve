package com.example.asus.shiboqi;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

/**
 * Created by asus on 2018/6/11.
 */

public class DrawUtil {

    /**
     * 绘制穿过多边形顶点的平滑曲线
     * 用三阶贝塞尔曲线实现
     * @param canvas 画布
     * @param points 多边形的顶点
     * @param k 控制点系数，系数越小，曲线越锐利
     * @param color 线条颜色
     * @param strokeWidth 线条宽度
     */
    public static void drawCurvesFromPoints(Canvas canvas, List<Point> points, double k, int color, float strokeWidth) {
        int size = points.size();
        Paint paint = new Paint();

        // 计算中点
        Point[] midPoints = new Point[size];
        for (int i = 0; i < size; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % size);
            midPoints[i] = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        }

        // 计算比例点
        Point[] ratioPoints = new Point[size];
        for (int i = 0; i < size; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % size);
            Point p3 = points.get((i + 2) % size);
            double l1 = distance(p1, p2);
            double l2 = distance(p2, p3);
            double ratio = l1 / (l1 + l2);
            Point mp1 = midPoints[i];
            Point mp2 = midPoints[(i + 1) % size];
            ratioPoints[i] = ratioPointConvert(mp2, mp1, ratio);
        }

        // 移动线段，计算控制点
        Point[] controlPoints = new Point[size * 2];
        for (int i = 0, j = 0; i < size; i++) {
            Point ratioPoint = ratioPoints[i];
            Point verPoint = points.get((i + 1) % size);
            float dx = ratioPoint.x - verPoint.x;
            float dy = ratioPoint.y - verPoint.y;
            Point controlPoint1 = new Point(midPoints[i].x - dx, midPoints[i].y - dy);
            Point controlPoint2 = new Point(midPoints[(i + 1) % size].x - dx, midPoints[(i + 1) % size].y - dy);
            controlPoints[j++] = ratioPointConvert(controlPoint1, verPoint, k);
            controlPoints[j++] = ratioPointConvert(controlPoint2, verPoint, k);
        }

        // 用三阶贝塞尔曲线连接顶点
        Path path = new Path();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < size; i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get((i + 1) % size);
            Point controlPoint1 = controlPoints[(i * 2 + controlPoints.length - 1) % controlPoints.length];
            Point controlPoint2 = controlPoints[(i * 2) % controlPoints.length];
            path.reset();
            path.moveTo(startPoint.x, startPoint.y);
            path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y);
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 计算两点之间的距离
     */
    private static double distance(Point p1, Point p2) {
        return Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)));
    }

    /**
     * 比例点转换
     */
    private static Point ratioPointConvert(Point p1, Point p2, double ratio) {
        Point p = new Point();
        p.x = (int) (ratio * (p1.x - p2.x) + p2.x);
        p.y = (int) (ratio * (p1.y - p2.y) + p2.y);
        return p;
    }
}