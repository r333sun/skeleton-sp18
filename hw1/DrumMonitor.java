import synthesizer.Drum;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */

public class DrumMonitor {
    private static String keyboardStr = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        Drum[] keyboard = new Drum[keyboardStr.length()];

        for (int i = 0; i < keyboard.length; i++) {
            Double freq = 440 * Math.pow(2,(i - 24)/12);
            keyboard[i] = new Drum(freq);
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
            for(Drum string: keyboard){
                sample += string.sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for(Drum string: keyboard){
                string.tic();
            }
        }
    }
}

