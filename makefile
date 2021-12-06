GameMain.class: GameMain.java
  javac GameMain.java
GameFrame.class: GameFrame.java
  javac GameFrame.java
GamePanel$AL.class: GamePanel.java
  javac GamePanel.java
GamePanel.class: GamePanel.java
  javac GamePanel.java
Sprite.class: Sprite.java
  javac Sprite.java
wall.class: wall.java
  javac wall.java
metor.class: metor.java
  javac metor.java
 
make run: GameMain.class
  java GameMain
  
make clean:
  rm *.class
