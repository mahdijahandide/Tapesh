//Mehdi Jahandide (Sherkat), [02.11.19 10:40]
package com.mhdi.tapesh;

import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mhdi.tapesh.BluetoothActivity;


public class ChartActivity extends AppCompatActivity {

    private final Handler mHandler = new Handler();
    private Runnable mTimer;
    private double graphLastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeries;

    public static BluetoothActivity bluetoothActivity;

    private GraphView graph;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        graph = findViewById(R.id.graph);
        initGraph(graph);
    }


    public void initGraph(GraphView graph) {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(1000);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);

        // first mSeries is a line
        mSeries = new LineGraphSeries<>();
        graph.addSeries(mSeries);
    }


    private float getAverage() {
        String currentData = bluetoothActivity.globalData.toString();
        char[] chars = currentData.toCharArray();
        float avg = 0;
        for (char a : chars) {
            float newPoint = (float) a;
            avg += newPoint;
        }
        avg /= chars.length;
        return avg;
    }

    private float getLastData() {
        String currentData = bluetoothActivity.globalData.toString();
        char[] chars = currentData.toCharArray();
        return (float) chars[0];
    }

    public void onResume() {
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
//                float avg = getAverage();
//                Toast.makeText(getApplicationContext(),String.valueOf(avg),Toast.LENGTH_LONG).show();

                float lastData = getLastData();
                Toast.makeText(getApplicationContext(),String.valueOf(lastData),Toast.LENGTH_LONG).show();

                if (graphLastXValue == 1000) {
                    graphLastXValue = 0;
                    mSeries.resetData(new DataPoint[]{
                            new DataPoint(graphLastXValue, lastData)
                    });
                }
                graphLastXValue += 1d;
                mSeries.appendData(new DataPoint(graphLastXValue,lastData), false, 1000);
                mHandler.postDelayed(this, 10);
            }
        };
        mHandler.postDelayed(mTimer, 700);
    }

    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }

    double mLastRandom = 2;

    private double getRandom() {
        mLastRandom++;
        return Math.sin(mLastRandom * 0.5) * 10 * (Math.random() * 10 + 1);
    }
}
//package com.mhdi.tapesh;
//
//
//import android.graphics.Color;
//import android.hardware.SensorEvent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//
//import java.io.UnsupportedEncodingException;
//
//import static com.mhdi.tapesh.BluetoothActivity.CONNECTING_STATUS;
//import static com.mhdi.tapesh.BluetoothActivity.MESSAGE_READ;
//import static com.mhdi.tapesh.BluetoothActivity.mHandler;
//
//public class ChartActivity extends AppCompatActivity{
//
//    private LineChart mChart;
//    private Thread thread;
//    private boolean plotData = true;
//    public static BluetoothActivity bluetoothActivity;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chart);
//
//
//
//        mChart = (LineChart) findViewById(R.id.chart1);
//
//        // enable description text
//        mChart.getDescription().setEnabled(true);
//
//        // enable touch gestures
//        mChart.setTouchEnabled(true);
//
//        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);
//        mChart.setDrawGridBackground(false);
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        mChart.setPinchZoom(true);
//
//        // set an alternative background color
//        mChart.setBackgroundColor(Color.WHITE);
//
//        LineData data = new LineData();
//        data.setValueTextColor(Color.WHITE);
//
//        // add empty data
//        mChart.setData(data);
//
//        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // modify the legend ...
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTextColor(Color.WHITE);
//
//        XAxis xl = mChart.getXAxis();
//        xl.setTextColor(Color.WHITE);
//        xl.setDrawGridLines(true);
//        xl.setAvoidFirstLastClipping(true);
//        xl.setEnabled(true);
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTextColor(Color.WHITE);
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMaximum(10f);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setDrawGridLines(true);
//
//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//        mChart.getAxisLeft().setDrawGridLines(false);
//        mChart.getXAxis().setDrawGridLines(false);
//        mChart.setDrawBorders(false);
//
//        feedMultiple();
//
//    }
//
//    private void addEntry() {
//
//
//        LineData data = mChart.getData();
//        ILineDataSet set = data.getDataSetByIndex(0);
//
//        Log.d("Test","Add entry is called") ;
//
//        if (data != null) {
//
//
//            // set.addEntry(...); // can be called as well
//
//            if (set == null) {
//                set = createSet();
//                data.addDataSet(set);
//            }
//            float test = Float.valueOf(bluetoothActivity.mReadBuffer.getText().toString());
//            Log.d("Test",String.valueOf(test)) ;
////            data.addEntry(new Entry(set.getEntryCount(),test), 0);
//            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 80) + 10f), 0);
//          // data.addEntry(new Entry(set.getEntryCount(), b.mHandler.h), 0);
//            data.notifyDataChanged();
//
//            // let the chart know it's data has changed
//            mChart.notifyDataSetChanged();
//
//            // limit the number of visible entries
//            mChart.setVisibleXRangeMaximum(150);
//            // mChart.setVisibleYRange(30, AxisDependency.LEFT);
//
//            // move to the latest entry
//            mChart.moveViewToX(data.getEntryCount());
//
//        }
//    }
//
//    private LineDataSet createSet() {
//
//        LineDataSet set = new LineDataSet(null, "Dynamic Data");
//        set.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set.setLineWidth(3f);
//        set.setColor(Color.MAGENTA);
//        set.setHighlightEnabled(false);
//        set.setDrawValues(false);
//        set.setDrawCircles(false);
//        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        set.setCubicIntensity(0.2f);
//        return set;
//    }
//
//    private void feedMultiple() {
//
//        if (thread != null) {
//            thread.interrupt();
//        }
//
//        thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                addEntry();
//                while (true) {
//                    plotData = true;
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        thread.start();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (thread != null) {
//            thread.interrupt();
//        }
//
//    }
//
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        thread.interrupt();
//        super.onDestroy();
//    }
//}
//
