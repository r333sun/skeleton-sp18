import synthesizer.GuitarString;

import java.util.HashMap;
import java.util.Map;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
import synthesizer.GuitarString;
public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static String keyboardStr = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString[] keyboard = new GuitarString[keyboardStr.length()];

        for (int i = 0; i < keyboard.length; i++) {
            Double freq = 440 * Math.pow(2,(i - 24)/12);
            keyboard[i] = new GuitarString(freq);
        }


        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int idx = keyboardStr.indexOf(key);
                if(idx == -1){
                    continue;
                }
                keyboard[idx].pluck();
            }

        /* compute the superposition of samples */
            double sample = 0;
            for(GuitarString string: keyboard){
                sample += string.sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for(GuitarString string: keyboard){
                string.tic();
            }
        }
    }
}

