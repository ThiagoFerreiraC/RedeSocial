import java.util.InputMismatchException;
import java.util.Scanner;

public class RedeSocial {
    static Scanner scan = new Scanner(System.in);
    static final String[][] INITIAL_MENU = {{"1", "Cadastrar-se"}, {"2", "Entrar"}, {"3", "Fechar"}};
    static final String[][] USER_MENU = {{"1", "Postar"}, {"2", "Timeline"}, {"3", "Acessar Menu dos Seguidores"}, {"4", "Sair"}};
    static final String[][] FOLLOWER_MENU = {{"1", "Timeline dos usuários"},{"2", "Seguir um usuário"}, {"3", "Visualizar quem você segue"}, {"4", "Visualizar seguidores"}, {"5", "Retornar ao Menu do Usuário"}};
    static String menuOption = "";
    static Profile[] profiles = new Profile[100];
    static int userID;
    static int profileCounter = 0;
    static int influencerID = 0;

    public static void main(String[] args) {
        printInitialMenu();
    }
    static void printInitialMenu() {
        System.out.println("\nSeja bem-vindo(a) ao Exemplário!");
        printMenuOptions(INITIAL_MENU);
        readMenuOption();
        getInitialMenuAction();
    }
    static void printMenuOptions(String[][] menu) {
        System.out.println("Digite uma das opções abaixo para começar:");
        for (int i = 0; i < menu.length; i++) {
            for (int j = 0; j < 1; j++) {
                System.out.printf("%s - para %s;", menu[i][j], menu[i][j+1]);
                System.out.println();
            }
        }
    }
    static String readMenuOption()  {
        System.out.print("Opção: ");
        return menuOption = scan.nextLine();
    }
    static void getInitialMenuAction() {
        switch (menuOption) {
            case "1":
                registerUser();
                break;
            case "2":
                logInToSocialNetwork();
                break;
            case "3":
                System.out.println("Esperamos te ver em breve!");
                break;
            default:
                System.out.println("Opção inválida!");
                printInitialMenu();
        }
    }
    static void registerUser() {
        System.out.println("\n----TELA DE CADASTRO");
        Profile user = new Profile();
        user.name = readUserRegistryData("nome");

        do {
            user.login = readUserRegistryData("login").toUpperCase();
        } while (!isLoginUnique(user.login));

        user.password = readUserRegistryData("senha");

        if (user.name.isBlank() || user.password.isBlank() || user.login.isBlank()){
            System.out.println("Um dos campos preenchidos está vazio");
            printInitialMenu();
        } else {
            checkPassword(user.password);
            profiles[profileCounter++] = user;
            printWelcomeRegistryMessage(user.name);
            printInitialMenu();
        }
    }
    static void printWelcomeRegistryMessage(String userName) {
        System.out.printf("Bem-vindo(a), %s! Agora escolha a opção entrar para que você possa aproveitar o Exemplário.\n", userName);
    }
    static String readUserRegistryData (String dataType) {
        String article = (dataType.equals("senha")) ? "a" : "o";
        System.out.printf("Digite %s %s: ", article, dataType);
        return scan.nextLine();
    }
    static boolean isLoginUnique(String userLogin) {
        for (int i = 0; i < profileCounter; i++) {
            if (userLogin.equals(profiles[i].login)) {
                System.out.printf("Esse login já está sendo utilizado. Por favor, escolha outro.\n");
                return false;
            }
        }
        return true;
    }
    static void checkPassword(String password) {
        System.out.print("Confirme a senha, digitando-a novamente: ");
        String checkedPassword = scan.nextLine();

        if (!checkedPassword.equals(password)) {
            System.out.println("Senhas são diferentes!");
            checkPassword(password);
        }
    }
    static void logInToSocialNetwork() {
        System.out.println("\n----TELA DE LOGIN");
        boolean isLoginSuccessfull = true;

        try {
            findUser();
            isPasswordCorrect();
        } catch (UserNotFoundException e) {
            System.out.println("Login não existe. É preciso se cadastrar primeiro.");
            isLoginSuccessfull = false;
        } catch (InvalidPasswordException e) {
            System.out.println("Senha inválida!.");
            isLoginSuccessfull = false;
        }

        if (isLoginSuccessfull) {
            printUserMenu();
        } else {
            printInitialMenu();
        }
    }
    static void findUser() throws UserNotFoundException{
        boolean isLoginFound = false;
        String entryLogin = readUserRegistryData("login").toUpperCase();

        for (int i = 0; i < profileCounter; i++) {
            if (entryLogin.equals(profiles[i].login)) {
                isLoginFound = true;
                userID = i;
                break;
            }
        }

        if (!isLoginFound) {
            throw new UserNotFoundException();
        }
    }
    static void isPasswordCorrect() throws InvalidPasswordException{
        String entryPassword = readUserRegistryData("senha");

        if (!profiles[userID].password.equals(entryPassword)) {
            throw new InvalidPasswordException();
        }
    }
    static void printUserMenu() {
        System.out.printf("\n----MENU DO USUÁRIO - Bem-vindo(a), %s!\n", profiles[userID].name);
        printMenuOptions(USER_MENU);
        readMenuOption();
        getUserMenuAction();
    }
    static void getUserMenuAction() {
        switch (menuOption) {
            case "1":
                post();
                printUserMenu();
                break;
            case "2":
                System.out.printf("\nSua Timeline, %s:\n", profiles[userID].name);
                profiles[userID].getOwnTimeline();
                printUserMenu();
                break;
            case "3":
                printFollowerMenu();
                break;
            case "4":
                printInitialMenu();
                break;
            default:
                System.out.println("Opção inválida. Por favor, digite novamente!");
                printUserMenu();
        }
    }
    static void post() {
        System.out.println("\n----POSTAR");
        String date = readDate();
        String hour = readHour();
        String comment = readComment();
        profiles[userID].post(date, hour, comment);
        System.out.println("Post realizado com sucesso!");
    }
    static String readDate() {
        System.out.print("Digite a data no formato DD/MM/AAAA: ");
        String date = scan.nextLine();

        if (date.length() != 10) {
            System.out.println("Formato de data inválido. Por favor, digite novamente.");
            date = readDate();
        }

        return date;
    }
    static String readHour() {
        System.out.print("Digite a hora no formato HH:MM: ");
        String hour = scan.nextLine();

        if (hour.length() != 5) {
            System.out.println("Formato de hora inválido. Por favor, digite novamente.");
            hour = readHour();
        }

        return hour;
    }
    static String readComment() {
        System.out.print("Digite o comentário: ");
        String comment = scan.nextLine();
        if (comment.isBlank()) {
            System.out.println("O comentário não pode ficar vazio");
            comment = readComment();
        }
        return comment;
    }
    static void printFollowerMenu() {
        System.out.printf("\n----MENU DOS SEGUIDORES\n");
        printMenuOptions(FOLLOWER_MENU);
        readMenuOption();
        getFollowerMenuAction();
    }
    static void getFollowerMenuAction() {
        switch (menuOption) {
            case "1":
                getInfluencerTimeline();
                printFollowerMenu();
                break;
            case "2":
                followInfluencer();
                printFollowerMenu();
                break;
            case "3":
                printInfluencersFollowedByUser();
                printFollowerMenu();
                break;
            case "4":
                printUsersThatFollowInfluencer();
                printFollowerMenu();
                break;
            case "5":
                printUserMenu();
                break;
            default:
                System.out.println("Opção inválida. Por favor, digite novamente!");
                printFollowerMenu();
        }
    }
    static void followInfluencer() {
        if (!isFirstUser()) {
            System.out.println("Digite o número correspondente à ID do usuário para segui-lo(a).");
            showUsersList();
            readInfluencer();
            scan.nextLine();
            addInfluencerToUser();
            addUserToInfluencer();
            System.out.printf("Usuário %s seguido com sucesso!\n", profiles[influencerID].name);
        }
    }
    static boolean isFirstUser() {
        if (profileCounter == 1) {
            System.out.println("Você é o nosso primeiro usuário. Que tal convidar os amigos?");
            return true;
        }

        return false;
    }
    static void showUsersList() {
        String format = "\n%-10s|%s\n";
        System.out.format(format, " Id do Usuário ", " Nome do Usuário ");

        for (int i = 0; i < profileCounter; i++) {
            if (i != userID) {
                System.out.printf("%14s | %s\n", i, profiles[i].name);
            }
        }
    }
    static int readInfluencer() {
        System.out.print("ID do Usuário: ");

        try {
            influencerID = scan.nextInt();

        } catch (InputMismatchException e) {
            System.out.println("Por favor, insira uma ID de usuário válida.");
            scan.nextLine();
            influencerID = readInfluencer();
        }

        if (influencerID >= profileCounter || influencerID < 0 || influencerID == userID) {
            System.out.println("Usuário não existe. Por favor, escolha novamente.");
            scan.nextLine();
            influencerID = readInfluencer();
        }

        return influencerID;
    }
    static void addInfluencerToUser() {
        int index = profiles[userID].influencerCount;
        profiles[userID].influencers[index] = profiles[influencerID].name;
        profiles[userID].influencerCount++;
    }
    static void addUserToInfluencer() {
        int index = profiles[influencerID].followersCount;
        profiles[influencerID].followers[index] = profiles[userID].name;
        profiles[influencerID].followersCount++;
    }
    static void printInfluencersFollowedByUser() {
        System.out.println("Lista de usuários que eu sigo:");

        if (profiles[userID].influencerCount == 0) {
            System.out.println("Você ainda não segue ninguém.");
        }

        for (int i = 0; i < profiles[userID].influencerCount; i++) {
            System.out.println(profiles[userID].influencers[i]);
        }
    }
    static void printUsersThatFollowInfluencer() {
        System.out.println("Lista de usuários que me seguem:");

        if (profiles[userID].followersCount == 0) {
            System.out.println("Você ainda não tem seguidores.");
        }

        for (int i = 0; i < profiles[userID].followersCount; i++) {
            System.out.println(profiles[userID].followers[i]);
        }
    }
    static void getInfluencerTimeline() {
        if (!isFirstUser()) {
            System.out.println("Digite o número correspondente à ID do usuário para ver sua timeline:");
            showUsersList();
            readInfluencer();
            scan.nextLine();
            System.out.printf("\nExibindo a Timeline do(a) %s\n", profiles[influencerID].name);
            profiles[influencerID].getInfluencerTimeline();
        }
    }
}

