package me.zkingofkill.spartan.rankup.utils;
import me.zkingofkill.spartan.rankup.SpartanRankup;

import java.text.DecimalFormat;

public class Utils {
    public static String numberFormat(double value) {
        String[] suffix = SpartanRankup.getInstance().getConfig().getStringList("decimalplaces").toArray(new String[0]);
        int index = 0;
        while ((value / 1000) >= 0) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value), suffix[index]);
    }
}
