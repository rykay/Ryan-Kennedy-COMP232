public class ForLoopClock {

    public static void main(String[] args) {
        int minutes, seconds, hours;
        for (hours = 1; hours <= 24; hours++) {
            for (minutes = 0; minutes < 60; minutes++) {
                for (seconds = 0; seconds < 60; seconds++) {
                    System.out.println(hours);
                    System.out.println(minutes);
                    System.out.println(seconds);
                }
            }
        }
    }
}