import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
 
public class PongGame extends Application
{
   private double paddleHeight = 60;
   private double paddleWidth = 10;
   private double paddley = 500/2 - paddleHeight/2;
   private double paddley2 = 500/2 - paddleHeight/2;
   private double ballx = 500/2;
   private double bally = 500/2;
   private double ballvx = -2.4;
   private double ballvy = 1.9;
   private double paddlev = 0;
   private double paddlev2 = 0;
   
   public static void main(String[] args)
   {
      launch(args);
   }
   
   public void draw(GraphicsContext gc)
   {
      gc.clearRect(0, 0, 500, 500);
      gc.fillRect(0, paddley, paddleWidth, paddleHeight);
      gc.fillRect(500 - paddleWidth, paddley2, paddleWidth, paddleHeight);
      gc.fillOval(ballx, bally, 10, 10);
   }
   
   public void update()
   {
      paddley += paddlev;
      paddley2 += paddlev2;
      ballx += ballvx;
      bally += ballvy;
      if(bally <= 0 || bally >= 500 - 10)
      {
         ballvy = -ballvy;
      }
      if(ballx <= paddleWidth && bally >= paddley && bally <= paddley + paddleHeight)
      {
         ballvx = -ballvx;
      }
      if(ballx >= 500 - paddleWidth && bally >= paddley2 && bally <= paddley2 + paddleHeight)
      {
         ballvx = -ballvx;
      }
      if(ballx < 0 || ballx > 500)
      {
         ballx = 250;
         bally = 250;
         ballvx = -ballvx;
      }
      if(paddley < 0)
      {
         paddley = 0;
      }
      if(paddley2 < 0)
      {
         paddley2 = 0;
      }
      if(paddley > 500 - paddleHeight)
      {
         paddley = 500 - paddleHeight;
      }
      if(paddley2 > 500 - paddleHeight)
      {
         paddley2 = 500 - paddleHeight;
      }
      
   }
   
   @Override
   public void start(Stage primaryStage)
   {
      primaryStage.setTitle("Ping-Pong");
      BorderPane root = new BorderPane();
      Canvas canvas = new Canvas(500, 500);//width x height
      root.setCenter(canvas);
      GraphicsContext gc = canvas.getGraphicsContext2D();
      Scene scene = new Scene(root);
      scene.setOnKeyPressed(event->
      {
      if(event.getCode() == KeyCode.W)
      {
         paddlev = -5;
      }
      if(event.getCode() == KeyCode.S)
      {
         paddlev = 5;
      }
      if(event.getCode() == KeyCode.UP)
      {
         paddlev2 = -5;
      }
      if(event.getCode() == KeyCode.DOWN)
      {
         paddlev2 = 5;
      }});
      scene.setOnKeyReleased(event->
      {
      if(event.getCode() == KeyCode.W)
      {
         paddlev = 0;
      }
      if(event.getCode() == KeyCode.S)
      {
         paddlev = 0;
      }
      if(event.getCode() == KeyCode.UP)
      {
         paddlev2 = 0;
      }
      if(event.getCode() == KeyCode.DOWN)
      {
         paddlev2 = 0;
      }});
      primaryStage.setScene(scene);
      primaryStage.show();
      new Thread(()->
      {
      while(true)
      {
         update();
         draw(gc);
         try{Thread.sleep(16);
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      }
      }).start();
   }
}
