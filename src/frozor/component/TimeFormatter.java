package frozor.component;

public class TimeFormatter {
    /*
    Number.prototype.toHumanReadable = function () {
        var days, hours, minutes, seconds = this;

        if(seconds <= 60) return `${seconds} seconds`;

        minutes = (seconds / 60).toFixed(1);

        if(minutes <= 60) return `${minutes} minutes`;

        hours   = (minutes / 60 ).toFixed(1);

        if(hours <= 60) return `${hours} hours`;

        return `${(hours/24).toFixed(1)} days`;
    };
     */

    public static String toHumanReadable(int seconds){
        double hours, minutes;
        hours = minutes = seconds;

        if(seconds <= 60) return String.format("%d Seconds", seconds);

        minutes = (seconds/60);

        if(minutes <= 60) return String.format("%.1f Minutes", minutes);

        hours = (minutes/60);

        if(hours <= 60) return String.format("%.1f Hours", hours);

        return String.format("%.1f Days", hours/24);
    }
}
