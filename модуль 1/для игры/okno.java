// Подключения необходимых библиотек
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Класс окна, в котором размещено игровое поле
class okno extends JFrame
{	
    private pole gameP; // Закрытая Переменная класса - игровое поле
    private int slogn; // Закрытая Переменная класса - сложность игры
    
    // Обработчик событий нажатий на клавиши
	private class myKey implements KeyListener  
	{
		    // Метод, который срабатывает при нажатии
	   	    public void keyPressed(KeyEvent e)
	   	    {
	   	    	// Получение кода нажатой клавиши
	   	    	int key_ = e.getKeyCode();
	   	    	
	   	    	// Выход из программы, если нажат - Esc 
	   	    	if (key_==27) System.exit(0);	   	    		   	    	
	   	    	else if (key_==37) // Если нажата стрелка влево
	   	    	{
	   	    		// Контроль перемещения влево за пределы окна
	   	    		if (gameP.x-30>-48) gameP.x-=30;
	   	    		else gameP.x=752;
	   	    	}	   	    	
	   	    	else if (key_==39) // Если нажата стрелка вправо
	   	    	{
	   	    		// Контроль перемещения вправо за пределы окна	   	    		
	   	    		if (gameP.x+30<752) gameP.x+=30;
	   	    		else gameP.x=-48;
	   	    	}
	   	    	
	   	    }
	   	    public void keyReleased(KeyEvent e) {}
	   	    public void keyTyped(KeyEvent e) {}
	   }            	
	
	// Конструктор класса
    public okno(int slogn)
    {
    	// Помещение сложности, выбранной пользователем в переменную класса
    	this.slogn = slogn;
    	// Подключение обработчика события для клавиатуры к окну    	
 	    addKeyListener(new myKey());
 	    // Установка активности окна
    	setFocusable(true);
    	
    	// Задание размеров и положения окна
        setBounds(0,0,800,600);
        // Задание заголовка окна
        setTitle("Игра: Новогодний дождь");
        // Запрет изменения размеров окна
        setResizable(false);
        // Операция завершения работы приложения при 
        // закрытии окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Создание объекта - игрового поля
        gameP = new pole(slogn);
        // Прикрепление (вложение) панели - игрового поля в окно
        Container con = getContentPane();
        con.add(gameP);
        
        // Сделать окно видимым 
    	setVisible(true);
    }
}

