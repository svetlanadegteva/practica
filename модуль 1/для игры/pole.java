// Подключения необходимых библиотек
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// Класс панели, которая является игровым полем
class pole extends JPanel
{
	  private Image shapka; // Закрытая Переменная класса, в которую загружается шапка
	  private Image fon; // Закрытая Переменная класса, в которую загружается фон
	  public int x = 400; // Открытая Переменная класса, в которую загружается шапка
	  private int slogn; // Переменная сложности игры
	  private podar[] gamePodar; // Массив подарков
	  private Image end_game; // Изображение Окончания игры
	  public Timer timerUpdate,timerDraw; // Два таймера: первый для 
	  	  
	   // Конструктор класса 
	   public pole(int slogn)
	   {		   
		   this.slogn = slogn;			  
		   // Загрузка изображения шапки из файла
		   try
		   {
		     shapka = ImageIO.read(new File("./shapka.png"));
		   }
		   catch(IOException ex) {}
		   
		   // Загрузка изображения фона из файла
		   try
		   {
		     fon = ImageIO.read(new File("./fon.png"));
		   }
		   catch(IOException ex) {}
		   
    	   // Загрузка изображения Окончания игры
		   try
		   {
		     end_game = ImageIO.read(new File("./end_game.png"));
		   }
		   catch(IOException ex) {}		   
		
    		//  Загрузка семи изображений подарков
		   gamePodar = new podar[7];
		   for (int i=0;i<7;i++)
		   {
			   try
			   {
				   gamePodar[i] = new podar(ImageIO.read(new File("./p"+i+".png")));
			   }
			   catch (IOException ex) {}
		   }

		   // Создание таймера, который будет раз в три секунды проверять и добавлять подарки на игровое поле 		   
		   timerUpdate = new Timer(3000,new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
		           updateStart(); // Метод для проверки и добавление подарков на игровое поле
				}
			});		    
		   timerUpdate.start(); // Запуск таймера timerUpdate 		   		   
		   
		   // Создание таймера, который будет перерисовывать игровое поле 20 раз в секунду 	   
		   timerDraw = new Timer(50,new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
		           repaint(); // Запуск метода перерисовки поля (public void paintComponent(Graphics gr))
				}
			});		    
		   timerDraw.start(); // Запуск таймера для перерисовки
		    			    		    		    		    		    
	   }
	   	   
	   // Метод, который отрисовывает графические объекты на панели
	   public void paintComponent(Graphics gr)
	   {
		   // Выполнить отрисовку сначала самого окна
		   super.paintComponent(gr);
		   gr.drawImage(fon, 0, 0, null); // Рисование фона 
		   gr.drawImage(shapka, x, 465, null); // Рисование шапки
		   // Цикл, который отображает подарки на игровом поле и проверяет пропущенные подарки
           for (int i=0;i<7;i++)
           {
        	       gamePodar[i].draw(gr); // Отображение подарка
        	       if (gamePodar[i].act==true) // Если подарок из массива подарков активен
        	       {
        		   if ((gamePodar[i].y+120)>=470) // Если подарок достиг нижней границы
        		   {
        			    if (Math.abs(gamePodar[i].x - x) > 75) // Если подарок пропущен
        			    {
        			        gr.drawImage(end_game, 300, 300, null); // Вывод картинки Окончания игры
        			    	timerDraw.stop(); // Оставнока таймера timerDraw 
        			    	timerUpdate.stop(); // Оставнока таймера timerUpdate
        			        break; // Прерывание цикла
        			    }
        			    else gamePodar[i].act=false; // Снятие подарка с игрового поля, если он пойман шапкой
        		   }
        	       }
           }
	   }
    	// Метод для проверки и добавление подарков на игровое поле
	   private void updateStart()
	   {
           int kol=0; // Переменная для подсчета подарков на игровом поле
           for (int i=0;i<7;i++) // Цикл перебора всех подарков массива
           {
              if (gamePodar[i].act==false) // Если подарок не на игровом поле
              {
                 if (kol<slogn) // Если текущее количество менее номера сложности (от 1 до 7)
                 {
                   gamePodar[i].start(); // Активизация подарка на игровом поле, вывод его сверху игрового поля
                   break; // Прерывание цикла
                 }
              }
              else kol++; // Если подарок на игровом поле
           }
	   }	   
}