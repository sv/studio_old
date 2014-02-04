/* Studio for kdb+ by Charles Skelton
   is licensed under a Creative Commons Attribution-Noncommercial-Share Alike 3.0 Germany License
   http://creativecommons.org/licenses/by-nc-sa/3.0
   except for the netbeans components which retain their original copyright notice
*/

package studio.ui;

import java.awt.Color;
import studio.kdb.K;
import studio.kdb.KTableModel;
import studio.kdb.ToDouble;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.TimeZone;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.SeriesDataset;
import studio.kdb.Config;

public class LineChart {

    private static XYDataset createTimeSeriesCollection(TimeZone tz, KTableModel table, Class klass) {
        TimeSeriesCollection tsc = new TimeSeriesCollection(tz);
        for (int col = 1; col < table.getColumnCount(); col++) {
            TimeSeries series = null;
            try {
                if (klass == K.KDateVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Day.class);
                    K.KDateVector dates = (K.KDateVector) table.getColumn(0);
                    for (int row = 0; row < dates.getLength(); row++) {
                        K.KDate date = (K.KDate) dates.at(row);
                        Day day = new Day(date.toDate(), tz);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(day, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KTimeVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Millisecond.class);
                    K.KTimeVector times = (K.KTimeVector) table.getColumn(0);
                    for (int row = 0; row < table.getRowCount(); row++) {
                        K.KTime time = (K.KTime) times.at(row);
                        Millisecond ms = new Millisecond(time.toTime(), tz);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(ms, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KTimestampVector.class) {
                    series = new TimeSeries(table.getColumnName(col));
                    K.KTimestampVector dates = (K.KTimestampVector) table.getColumn(0);
                    for (int row = 0; row < dates.getLength(); row++) {
                        K.KTimestamp date = (K.KTimestamp) dates.at(row);
                        FixedMillisecond day = new FixedMillisecond(date.toTimestamp().getTime());
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(day, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KTimespanVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Millisecond.class);
                    K.KTimespanVector times = (K.KTimespanVector) table.getColumn(0);
                    for (int row = 0; row < table.getRowCount(); row++) {
                        K.KTimespan time = (K.KTimespan) times.at(row);
                        Millisecond ms = new Millisecond(time.toTime(), tz);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(ms, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KDatetimeVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Millisecond.class);
                    K.KDatetimeVector times = (K.KDatetimeVector) table.getColumn(0);
                    for (int row = 0; row < table.getRowCount(); row++) {
                        K.KDatetime time = (K.KDatetime) times.at(row);
                        Millisecond ms = new Millisecond(time.toTimestamp(), tz);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(ms, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KMonthVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Month.class);
                    K.KMonthVector times = (K.KMonthVector) table.getColumn(0);
                    for (int row = 0; row < table.getRowCount(); row++) {
                        K.Month time = (K.Month) times.at(row);
                        int m = time.i + 24000;
                        int y = m / 12;
                        m = 1 + m % 12;
                        Month month = new Month(m, y);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(month, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KSecondVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Second.class);
                    K.KSecondVector times = (K.KSecondVector) table.getColumn(0);
                    for (int row = 0; row < table.getRowCount(); row++) {
                        K.Second time = (K.Second) times.at(row);
                        Second second = new Second(time.i % 60, time.i / 60, 0, 1, 1, 2001);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(second, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                } else if (klass == K.KMinuteVector.class) {
                    series = new TimeSeries(table.getColumnName(col), Minute.class);
                    K.KMinuteVector times = (K.KMinuteVector) table.getColumn(0);
                    for (int row = 0; row < table.getRowCount(); row++) {
                        K.Minute time = (K.Minute) times.at(row);
                        Minute minute = new Minute(time.i % 60, time.i / 60, 1, 1, 2001);
                        Object o = table.getValueAt(row, col);
                        if (o instanceof K.KBase) {
                            if (!((K.KBase) o).isNull()) {
                                if (o instanceof ToDouble) {
                                    series.addOrUpdate(minute, ((ToDouble) o).toDouble());
                                }
                            }
                        }
                    }
                }
            } catch (SeriesException e) {
                System.err.println("Error adding to series");
            }
            if (series.getItemCount() > 0) {
                tsc.addSeries(series);
            }
        }
        return tsc;
    }

    private static XYDataset createXY(KTableModel table) {
        XYSeriesCollection xysc = new XYSeriesCollection();
        boolean syntheticColumn = table.getColumnCount()==1;
        int firstColumn =syntheticColumn?0:1;    
        for (int col = firstColumn; col < table.getColumnCount(); col++) {
            XYSeries series = null;
            try {
                series = new XYSeries(table.getColumnName(col));
                for (int row = 0; row < table.getRowCount(); row++) {
                    double x;
                    if (!syntheticColumn) {
                        x = ((ToDouble) table.getValueAt(row, 0)).toDouble();
                    } else {
                        x = row;
                    }
                    double y = ((ToDouble) table.getValueAt(row, col)).toDouble();
                    series.add(x, y);
                }
            } catch (SeriesException e) {
                System.err.println("Error adding to series");
            }
            if (series!=null && series.getItemCount() > 0) {
                xysc.addSeries(series);
            }
        }
        return xysc;
    }
    private static CategoryDataset createBarChart(KTableModel table){
        DefaultCategoryDataset cds = new DefaultCategoryDataset();
        for (int col = 1; col < table.getColumnCount(); col++) {

            try {
                for (int row = 0; row < table.getRowCount(); row++) {
                    cds.addValue(((ToDouble) table.getValueAt(row,col)).toDouble(),String.valueOf(table.getValueAt(row,0)),table.getColumnName(col));
                }
            } catch (SeriesException e) {
                System.err.println("Error adding to series");
            }
        }
        return cds;
    }
    public ChartPanel chartPanel;
    JFrame frame = null;

    public LineChart(KTableModel table) {
        JFreeChart chart = createDataset(table);
        if (chart != null) {
            frame = new JFrame("Studio for kdb+ [chart]");

            chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500,270));
            chartPanel.setMouseZoomable(true,false);
            frame.setContentPane(chartPanel);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frame.setIconImage(Util.getImage(Config.imageBase2 + "chart_24.png").getImage());

            frame.pack();
            frame.setVisible(true);
            frame.requestFocus();
            frame.toFront();
        }
    }

    public static JFreeChart createDataset(KTableModel table) {
        TimeZone tz = TimeZone.getTimeZone("GMT");

        Dataset ds = null;

        if (table.getColumnCount() > 0) {
            Class klass = table.getColumnClass(0);

            if ((klass == K.KTimestampVector.class) ||(klass == K.KTimespanVector.class) || (klass == K.KDateVector.class) || (klass == K.KTimeVector.class) || (klass == K.KMonthVector.class) || (klass == K.KMinuteVector.class) || (klass == K.KSecondVector.class) || (klass == K.KDatetimeVector.class)) {

                ds = createTimeSeriesCollection(tz, table, klass);
            }
            else if ((klass == K.KDoubleVector.class) || (klass == K.KFloatVector.class) || (klass == K.KShortVector.class) || (klass == K.KIntVector.class) || (klass == K.KLongVector.class)) {

                ds = createXY(table);
            } else if( klass == K.KSymbolVector.class){
                ds= (CategoryDataset)createBarChart(table);
            }
        }

        if (ds != null) {
            boolean legend = false;

            if (ds instanceof  SeriesDataset && ((SeriesDataset)ds).getSeriesCount() > 1)
                legend = true;

            if (ds instanceof XYSeriesCollection)
                return ChartFactory.createXYLineChart("",
                                                      "",
                                                      "", (XYDataset) ds,
                                                      PlotOrientation.VERTICAL,
                                                      legend,
                                                      true,
                                                      true);
            else if (ds instanceof TimeSeriesCollection)
                return ChartFactory.createTimeSeriesChart("",
                                                          "",
                                                          "", (XYDataset) ds,
                                                          legend,
                                                          true,
                                                          true);
            else if (ds instanceof CategoryDataset)
               return ChartFactory.createBarChart("", "", "", (CategoryDataset) ds, PlotOrientation.VERTICAL, true, true, true);
        }

        return null;
    }
}

