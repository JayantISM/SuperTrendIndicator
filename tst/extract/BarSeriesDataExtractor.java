package extract;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.num.DecimalNum;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;

public class BarSeriesDataExtractor {
    public BarSeries extractBarSeriesData(String fileName) throws IOException {
        final String barSeriesJSONString = readBarSeriesStringFromFile(fileName);
        final JSONObject barSeriesJsonObj = new JSONObject(barSeriesJSONString);
        final JSONArray barSeriesArr = barSeriesJsonObj.getJSONArray("candles");
        BarSeries series = new BaseBarSeriesBuilder().withName("BANKNIFTY22MAR36400CE").build();
        for(int i=0;i<barSeriesArr.length();i++) {
            JSONArray barData = (JSONArray) barSeriesArr.get(i);
//            System.out.println(barData);
//            for (int j=0;j<barData.length();j++) {
//                System.out.println("parsing - " + barData.get(j));
                ZonedDateTime startTime = ZonedDateTime.parse((CharSequence) barData.get(0));
                ZonedDateTime endTime = startTime.plusSeconds(179);
                Number open = (Number)barData.get(1);
            Number close = (Number)barData.get(4);
            Number high = (Number)barData.get(2);
            Number low = (Number)barData.get(3);
            Number volume = (Number) barData.get(5);
                BaseBar bar = BaseBar.builder(DecimalNum::valueOf, Number.class)
                        .timePeriod(Duration.ofMinutes(3))
                        .endTime(endTime)
                        .openPrice(open)
                        .highPrice(high)
                        .lowPrice(low)
                        .closePrice(close)
                        .volume(volume)
                        .build();
                series.addBar(bar);
        }
        return series;
    }

    private String readBarSeriesStringFromFile(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
            sb.append(new String(buffer));
            buffer = new byte[10];
        }
        fis.close();
        return sb.toString();
    }
}
