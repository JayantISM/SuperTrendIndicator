import extract.BarSeriesDataExtractor;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import sp.supertrend.SuperTrendIndicator;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class SupertrendIndicatorTest {
    private static final BarSeriesDataExtractor barSeriesDataExtractor = new BarSeriesDataExtractor();
    private static BarSeries barSeries = null;

    static {
        try {
            barSeries = barSeriesDataExtractor.extractBarSeriesData("/Users/jayanttiwari/IdeaProjects/SupertrendIndicator/tst/data/SampleBANKNIFTYData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final SuperTrendIndicator superTrendIndicator = new SuperTrendIndicator(barSeries, 3.0, 10);

    public static void main(String[] args) {
        System.out.println("BarSeries Count is: " + barSeries.getBarCount());
        List<Bar> barList = barSeries.getBarData();
        System.out.println(superTrendIndicator.getSeries().getBarCount());
        for (int i=4;i<barSeries.getBarCount();i++) {
            Bar barData = barList.get(i);
            System.out.println("[ " + barData.getBeginTime() + ", " + superTrendIndicator.getValue(i) + ", " + superTrendIndicator.getSignal(i) + " ]");
        }
    }
}
