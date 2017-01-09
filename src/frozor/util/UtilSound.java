package frozor.util;

public class UtilSound {
    public static float getNoteBlockPitch(float pitch){
        return (float) Math.pow(2.0, (((double)pitch - 12.0) / 12.0));
    }
}
