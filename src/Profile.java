public class Profile {
    String name;
    String login;
    String password;
    int influencerCount = 0;
    int followersCount = 0;
    String[] influencers = new String[100];
    String[] followers = new String[100];
    Post[] posts = new Post[100];
    int postsCount = 0;
    void post(String date, String hour, String comment) {
        Post post = new Post();
        post.date = date;
        post.hour = hour;
        post.comment = comment;
        posts[postsCount++] = post;
    }
    void getOwnTimeline() {
        if (postsCount == 0) {
            System.out.println("Você ainda não fez nenhuma postagem!");
        } else {
            for (int i = 0; i < postsCount; i++) {
                posts[i].printTimeline();
            }
        }
    }
    void getInfluencerTimeline() {
        if (postsCount == 0) {
            System.out.println("Este usuário ainda não fez nenhum post.");
        } else {
            for (int i = 0; i < postsCount; i++) {
                posts[i].printTimeline();
            }
        }
    }
}

