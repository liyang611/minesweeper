import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class saolei implements ActionListener {
    JFrame frame=new JFrame("minesweeper-扫雷游戏");
    JButton reset=new JButton("start again-重来");
    Container container=new Container();
    //游戏数据
    final int row =20;
    final int cor=20;
    final int leiCount=30;
    JButton [][]buttons=new JButton[row][cor];
    int [][]counts=new int[row][cor];
    final int LEICODE=10;
    //构造函数
    public saolei(){
        //设置窗口
        frame.setSize(900,800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        //添加重来按钮
        addResetButton();
        //添加按钮
        addButtons();
        //埋雷
        addLei();
        //添加雷的计算
    }
    public void addResetButton(){
        reset.setBackground(Color.green);
        reset.setOpaque(true);
        reset.addActionListener(this);
        frame.add(reset,BorderLayout.NORTH);
    }
    public void addLei(){
        Random rand=new Random();
        int randRow,randCol;
        for (int i=0;i<leiCount;i++){
            randRow=rand.nextInt(row);
            randCol=rand.nextInt(cor);
        if (counts[randRow][randCol]==LEICODE){i--;}
        else {counts[randRow][randCol]=LEICODE;}
        }
    }
    public void addButtons(){
        frame.add(container,BorderLayout.CENTER);
        container.setLayout(new GridLayout(row,cor));
        for(int i=0;i<row;i++){
            for(int j=0;j<cor;j++){
                JButton button=new JButton();
                button.setBackground(Color.yellow);
                button.setOpaque(true);
                button.addActionListener(this);
                buttons[i][j]=button;
                container.add(button);
            }
        }
    }
    public void calcNeiboLei(){
        int count;
        for(int i=0;i<row;i++){
            for(int j=0;j<cor;j++){
                count=0;
                if(counts[i][j]==LEICODE) continue;

                if(i>0 && j>0 && counts[i-1][j-1]==LEICODE) count++;
                if(i>0&&counts[i-1][j]==LEICODE) count++;
                if(i>0 && j<19 && counts[i-1][j+1]==LEICODE) count++;
                if(j>0 && counts[i][j-1]==LEICODE) count++;
                if(j<19 && counts[i][j+1]==LEICODE) count++;
                if(i<19&&j>0&&counts[i+1][j-1]==LEICODE) count++;
                if(i<19&&counts[i+1][j]==LEICODE) count++;
                if(i<19&&j<19&&counts[i+1][j+1]==LEICODE) count++;

                counts[i][j]=count;
                //buttons[i][j].setText(counts[i][j]+"");
            }
        }
    }
    public void actionPerformed(ActionEvent actionEvent) {
        JButton button=(JButton)actionEvent.getSource();
        if(button.equals(reset)){
            for(int i=0;i<row;i++){
                for(int j=0;j<cor;j++){
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setBackground(Color.yellow);
                    counts[i][j]=0;
                }
            }
            addLei();
            calcNeiboLei();
        }else{
            int count=0;
            for(int i=0;i<row;i++){
                for(int j=0;j<cor;j++){
                    if(button.equals(buttons[i][j])){
                        count=counts[i][j];
                        if(count==LEICODE){
                            LoseGame();
                        }else{
                            openCell(i,j);
                            checkWin();
                        } return;
                    }
                }
            }


        }
    }
    void checkWin(){
        for(int i=0;i<row;i++){
            for(int j=0;j<cor;j++){
                if(buttons[i][j].isEnabled()==true && counts[i][j]!=LEICODE) return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Yeah,你赢了！");
    }
    void openCell(int i,int j){
        if(buttons[i][j].isEnabled()==false) return;

        buttons[i][j].setEnabled(false);


        if(counts[i][j]==0){
            if(i>0 && j>0 && counts[i-1][j-1]!=LEICODE) openCell(i-1, j-1);
            if(i>0&&counts[i-1][j]!=LEICODE) openCell(i-1, j);
            if(i>0 && j<19 && counts[i-1][j+1]!=LEICODE) openCell(i-1, j+1);
            if(j>0 && counts[i][j-1]!=LEICODE) openCell(i, j-1);
            if(j<19 && counts[i][j+1]!=LEICODE) openCell(i, j+1);
            if(i<19&&j>0&&counts[i+1][j-1]!=LEICODE) openCell(i+1, j-1);
            if(i<19&&counts[i+1][j]!=LEICODE) openCell(i+1, j);
            if(i<19&&j<19&&counts[i+1][j+1]!=LEICODE) openCell(i+1, j+1);

            buttons[i][j].setText(counts[i][j]+"");
        }else{
            buttons[i][j].setText(counts[i][j]+"");
        }
    }
    void LoseGame(){
        for(int i=0;i<row;i++){
            for(int j=0;j<cor;j++){
                int count=counts[i][j];
                if(count==LEICODE){
                    buttons[i][j].setText("X");
                    buttons[i][j].setBackground(Color.red);
                    buttons[i][j].setEnabled(false);
                }else{
                    buttons[i][j].setText(count+"");
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }
    public static void main(String[] args) {
        saolei lei=new saolei();
    }


}

