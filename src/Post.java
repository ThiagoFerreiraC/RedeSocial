public class Post {
    String date;
    String hour;
    String comment;
    void printTimeline() {
        System.out.printf("%20s às %s - \"%s\"\n", date, hour, comment);
    }
}
