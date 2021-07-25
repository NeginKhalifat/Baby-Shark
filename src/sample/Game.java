package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private int ID;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    Hook hook;
    Player player;
    ScreenManager sn;
    GameStatus gameStatus;
    FishGenerator fishGenerator;
    ShellGenerator shellGenerator;
    private boolean stop = false;

    public Game(Player player1, ScreenManager sn1, FishGenerator generator, ShellGenerator shellGenerator1) throws IOException {
        ID = atomicInteger.incrementAndGet();
        player = player1;
        hook = sn1.getHook();
        fishGenerator = generator;
        shellGenerator = shellGenerator1;
        sn = sn1;
        start();
    }

    public void endGame(ScreenManager sn, float score) {
        for (Fish fish :
                sn.getFishes()) {
            if (fish.fishType == FishType.SHOT) {
                fish.getshot().stop();
            }
            fish.stop();
        }

        sn.getPane().getChildren().removeAll();
        Parent root;
        try {
            if (gameStatus == GameStatus.LOOSE) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/lose.fxml"));
                root = loader.load();
                LoseController loseController = loader.getController();
                loseController.PassUsername(sn.username);
                sn.getPane().getScene().setRoot(root);

            } else if (gameStatus == GameStatus.WIN) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/win.fxml"));

                root = loader.load();
                WinController winController = loader.getController();
                winController.PassUsername(sn.username);
                winController.setScore(score);
                sn.getPane().getScene().setRoot(root);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void start() throws IOException {
        hook.line.endXProperty().bind(hook.imageView.xProperty());
        hook.line.endYProperty().bind(hook.imageView.yProperty());

        Timer timer = new Timer();
        Timeline timeline = new Timeline();


        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                timeline.setCycleCount(10);
                KeyFrame kf = new KeyFrame(javafx.util.Duration.millis(1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        float random = (float) Math.random();
                        int space_height = (int) hook.getImageView().getBoundsInParent().getHeight();
                        int space_width = (int) hook.getImageView().getBoundsInParent().getWidth();

                        if (random < .25) {
                            double newX = hook.imageView.getX() + hook.speed.getNum() * 20;
                            if (newX > 700 - space_width) {
                                newX = (int) hook.imageView.getX() - hook.speed.getNum() * 20;
                            }

                            hook.imageView.setX(newX);


                        } else if (random < .5) {
                            double newX = hook.imageView.getX() - hook.speed.getNum() * 20;
                            if (newX < space_width) {
                                newX = (int) hook.imageView.getX() + hook.speed.getNum() * 20;
                            }

                            hook.imageView.setX(newX);


                        } else if (random < .75) {
                            double newY = hook.imageView.getY() + hook.speed.getNum() * 20;
                            if (newY > 500 - space_height) {
                                newY = (int) hook.imageView.getY() - hook.speed.getNum() * 20;
                            }

                            hook.imageView.setY(newY);


                        } else {
                            double newY = (hook.imageView.getY() - hook.speed.getNum() * 20);
                            if (newY < space_height) {
                                newY = (int) (hook.imageView.getY() + hook.speed.getNum() * 20);
                            }

                            hook.imageView.setY(newY);


                        }
                    }

                });
                timeline.getKeyFrames().add(kf);
                timeline.play();

            }
        }, 10 * 1000, 10 * 1000);

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (sn.stop) {

                    sn.getMyTimer().stop();
                    stop = true;
                    fishGenerator.stop();
                    this.stop();
                }
                if (player.getScore() < 0) {

                    sn.getMyTimer().stop();
                    gameStatus = GameStatus.LOOSE;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    try {
                        SaveToFile(ID, formatter.format(date), sn.getMyTimer().getTime(), gameStatus, sn.username, player.getScore());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stop = true;
                    fishGenerator.stop();
                    this.stop();
                }


                if (player.getFishPlayer().is_collide(hook)) {
                    if (!player.getFishPlayer().has_Shield) {
                        if (player.getFishPlayer().lives < 2) {//todo
                            timeline.stop();
                            player.getFishPlayer().getImageView().xProperty().bind(hook.getImageView().xProperty());
                            player.getFishPlayer().getImageView().yProperty().bind(hook.getImageView().yProperty());


                            hook.gotoXY(hook.getImageView().getX(), 10);


                            hook.has_fish = true;

                            player.getFishPlayer().setX(hook.getX());
                            player.getFishPlayer().setY(hook.getY());
                            sn.getMyTimer().stop();
                            gameStatus = GameStatus.LOOSE;
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date date = new Date();

                            try {
                                SaveToFile(ID, formatter.format(date), sn.getMyTimer().getTime(), gameStatus, sn.username, player.getScore());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            timeline.stop();
                            stop = true;
                            this.stop();


                        } else {

                            timeline.pause();
                            player.getFishPlayer().getImageView().xProperty().bind(hook.getImageView().xProperty());
                            player.getFishPlayer().getImageView().yProperty().bind(hook.getImageView().yProperty());
                            hook.gotoXY(hook.getImageView().getX(), 10);
                            player.getFishPlayer().setX(hook.getX());
                            player.getFishPlayer().setY(hook.getY());
                            player.getFishPlayer().getImageView().xProperty().unbind();
                            player.getFishPlayer().getImageView().yProperty().unbind();

                            player.getFishPlayer().lives -= 1;
                            sn.lives.setText("Lives: " + player.getFishPlayer().lives);
                            player.getFishPlayer().getImageView().setY(0);
                            player.getFishPlayer().getImageView().setX(0);


                        }

                    }
                }

                for (int i = 0; i < sn.getShells().size(); i++) {
                    if (stop) break;
                    Shell shell = sn.getShells().get(i);
                    if (player.getFishPlayer().is_collide(shell)) {

                        player.setScore(shell.score + player.getScore());
                        sn.removeShell(shell);
                        if (player.getFishPlayer().hasBullet && !player.getFishPlayer().has_Shield) {
                            player.getFishPlayer().CreateShield(sn.getPane());
                        } else if (!player.getFishPlayer().hasBullet) {
                            Random random = new Random();

                            sn.getPane().getScene().setOnKeyPressed(e -> {
                                if (e.getCode() == KeyCode.F) {
                                    try {
                                        player.getFishPlayer().bullet = new Bullet(player.getFishPlayer()
                                        );
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                    sn.getPane().getChildren().add(player.getFishPlayer().bullet.getImageView());
                                    shot(1 + random.nextInt(12), random.nextBoolean(), player.getFishPlayer().bullet, player.getFishPlayer().speed, player.getFishPlayer().direction);
                                }
                            });
                            player.getFishPlayer().hasBullet = true;

                        }
                        if (player.getFishPlayer().Is_winner()) {
                            sn.getMyTimer().stop();
                            gameStatus = GameStatus.WIN;
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date date = new Date();

                            try {
                                SaveToFile(ID, formatter.format(date), sn.getMyTimer().getTime(), gameStatus, player.getProfile().user_name, player.getScore());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            stop = true;
                        }
                    }
                }
                for (int i = 0; i < sn.getFishes().size(); i++) {
                    if (stop) break;
                    Fish fish = sn.getFishes().get(i);
                    if (fish.fishType == FishType.SHOT) {
                        if (player.getFishPlayer().is_collide(((ShotFish) fish).getshot()) && !player.getFishPlayer().has_Shield) {
                            player.setScore(player.getScore() - 1);
                            player.getFishPlayer().imageView.setFitWidth(player.getFishPlayer().width -= 1);
                            player.getFishPlayer().width -= 1;
                            player.getFishPlayer().setFitHeight(player.getFishPlayer().height -= 1);
                            player.getFishPlayer().height -= 1;


                        }
                    }

                    if (fish.is_collide(player.getFishPlayer())) {


                        if (player.getFishPlayer().canEatFish(fish)) {
                            player.setScore(fish.getFish_value() + player.getScore());
                            sn.removeFish(fish);
                            int temp = fish.getFish_value();
                            player.getFishPlayer().imageView.setFitWidth(player.getFishPlayer().width += temp);
                            player.getFishPlayer().width += temp;
                            player.getFishPlayer().setFitHeight(player.getFishPlayer().height += temp);
                            player.getFishPlayer().height += temp;
                            if (player.getFishPlayer().Is_winner()) {
                                sn.getMyTimer().stop();
                                gameStatus = GameStatus.WIN;
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();

                                try {
                                    SaveToFile(ID, formatter.format(date), sn.getMyTimer().getTime(), gameStatus, sn.username, player.getScore());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stop = true;
                                this.stop();
                                break;
                            }
                        } else if (!player.getFishPlayer().has_Shield) {

                            player.setScore(player.getScore() - fish.fish_value);
                            int temp = fish.getFish_value();
                            player.getFishPlayer().imageView.setFitWidth(player.getFishPlayer().width += temp);
                            player.getFishPlayer().width -= temp;
                            player.getFishPlayer().setFitHeight(player.getFishPlayer().height += temp);
                            player.getFishPlayer().height -= temp;

                            if (player.getScore() < 0) {
                                sn.getMyTimer().stop();
                                gameStatus = GameStatus.LOOSE;
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                try {
                                    SaveToFile(ID, formatter.format(date), sn.getMyTimer().getTime(), gameStatus, sn.username, player.getScore());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stop = true;
                                this.stop();
                                break;

                            }

                        }
                    }
                }
                if (stop) {

                    shellGenerator.stop();
                    fishGenerator.stop();
                    timeline.stop();
                    endGame(sn, player.getScore());
                }
            }
        };

        timer2.start();

    }


    private void shot(int dx, Boolean up, Bullet screenObject, Speed speed, char dir) {

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1000);
        KeyFrame kf = new KeyFrame(javafx.util.Duration.millis(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                char direction = dir;

                if (direction == 'r') {

                    screenObject.imageView.setX(screenObject.imageView.getX() + dx);

                } else {

                    screenObject.imageView.setX(screenObject.imageView.getX() - dx);
                }
                if (up) {
                    screenObject.imageView.setY(screenObject.imageView.getY() + 1);
                } else {
                    screenObject.imageView.setY(screenObject.imageView.getY() - 1);
                }
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }


    private void clearFish() {

        for (Fish fish : sn.getFishes()) {

            sn.getPane().getChildren().remove(fish);
            sn.getFishes().remove(fish);
        }
    }

    private void SaveToFile(int id, String format, String time, GameStatus gameStatus, String username, float score) throws IOException {
        try {
            FileOutputStream f = new FileOutputStream(new File("game_info.txt"), true);

            ObjectOutputStream o = new ObjectOutputStream(f);
            String string = id + "," + format + "," + time + "," + gameStatus.toString() + "," + score + "," + username + '\n';
            o.writeChars(string);
            o.close();
            f.close();


        } catch (FileNotFoundException e) {
            FileOutputStream f = new FileOutputStream(new File("game_info.txt"));

            ObjectOutputStream o1 = new ObjectOutputStream(f);
            String string = id + "," + format + "," + time + "," + gameStatus.toString() + "," + score + "," + username + '\n';
            o1.writeChars(string);
            o1.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



