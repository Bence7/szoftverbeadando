package org.mygame;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.mygame.model.MyPositionType;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class MiniChessController {

    private boolean isClicked=true;
    private MyPositionType pozicio=null;
    private final ArrayList<MyPositionType> circlePosition = new ArrayList<>();
    private final ArrayList<MyPositionType> enemyCirclePosition = new ArrayList<>();
    private final ArrayList<MyPositionType> forbiddenPosition = new ArrayList<>();
    private final ArrayList<MyPositionType> selectablePosition = new ArrayList<>();
    private final ArrayList<MyPositionType> selectableEnemyPosition = new ArrayList<>();
    private MyPositionType actualPosition;
    @FXML
    private GridPane grid;
    @FXML
    private void initialize() {
        fillArrays();
        createWall();
        createCircles();
    }

    private void fillArrays() {
        for (var j = 0; j < grid.getColumnCount(); j++) {
            circlePosition.add(new MyPositionType(j, 2));
            enemyCirclePosition.add(new MyPositionType(j,0));
        }
    }

    //táblán levő körök kirajzolása
    private void createCircles() {
        for (MyPositionType position : circlePosition){
            createBlueCircle(position.getColumn(), position.getRow());
        }
        for (MyPositionType position : enemyCirclePosition){
            createRedCircle(position.getColumn(), position.getRow());
        }
    }

    private void createBlueCircle(Integer j, Integer i) {
        Circle circle = new Circle(35);
        circle.getStyleClass().add("bluecircle");
        grid.add(circle, j, i);
        circle.setOnMouseClicked(this::handleMouseClick);
    }

    private void createRedCircle(Integer j, Integer i) {
        Circle circle = new Circle(35);
        circle.getStyleClass().add("redcircle");
        grid.add(circle, j, i);

    }

    private void createWall() {
        var wall1 = new Pane();
        var wall2 = new Pane();
        wall1.getStyleClass().add("wall");
        wall2.getStyleClass().add("wall");
        grid.add(wall1, 2, 3);
        grid.add(wall2, 4, 2);
        forbiddenPosition.add(new MyPositionType(2,3));
        forbiddenPosition.add(new MyPositionType(4,2));
    }

    private void handleMouseClick(MouseEvent event) {



        if(isClicked) removeSquare();
        else{bugfix();}

        var circle = (Node) event.getSource();
        var column = GridPane.getColumnIndex(circle);
        var row = GridPane.getRowIndex(circle);
        actualPosition=new MyPositionType(column,row);

        System.out.println("\nCirclePosition: "+circlePosition);
        System.out.println("EnemyCirclePosition: "+enemyCirclePosition);
        System.out.println("Actual position: "+actualPosition);

        selectablePosition(column, row);



        isClicked=false;
    }

   /* private void goal_test() {
        for (MyPositionType positionType : circlePosition) {
            int col=positionType.getColumn();
            int row=positionType.getRow();

            if (col > 0 && row > 0) selectablePositionGoalTest.add(new MyPositionType(col - 1, row - 1));
            if (row > 0) selectablePositionGoalTest.add(new MyPositionType(col, row - 1));
            if (col < 6 && row > 0) selectablePositionGoalTest.add(new MyPositionType(col + 1, row - 1));

            for (MyPositionType position : forbiddenPosition) {
                selectablePositionGoalTest.removeIf( node -> node.getColumn() == position.getColumn() && node.getRow() == position.getRow());
            }

            for( MyPositionType position : enemyCirclePosition){
                if(actualPosition.getColumn().equals(position.getColumn()) && position.getRow().equals(actualPosition.getRow()-1)){
                    selectablePositionGoalTest.removeIf(node -> node.getColumn() == position.getColumn() && node.getRow() == position.getRow());
                } else if (position.getColumn().equals(actualPosition.getColumn()-1) && position.getRow().equals(actualPosition.getRow()-1)) {
                    selectablePositionGoalTest.add(position);
                } else if (actualPosition.getColumn()+1==position.getColumn() && actualPosition.getRow()-1 == position.getRow()) {
                    selectablePositionGoalTest.add(position);
                }
            }
            for (MyPositionType position : circlePosition) {
                selectablePositionGoalTest.removeIf( node -> node.getColumn() == position.getColumn() && node.getRow() == position.getRow());
            }
            System.out.println(selectablePositionGoalTest.size());
            selectablePositionGoalTest.clear();
        }
    }*/
    private void selectablePosition(Integer col, Integer row) {
        //minden egyes választható pozíció beletevese a selectablePosition-be
        if (col > 0 && row > 0) selectablePosition.add(new MyPositionType(col - 1, row - 1));
        if (row > 0) selectablePosition.add(new MyPositionType(col, row - 1));
        if (col < 6 && row > 0) selectablePosition.add(new MyPositionType(col + 1, row - 1));

        //nem léphet a falra
        for (MyPositionType position : forbiddenPosition) {
            selectablePosition.removeIf( node -> node.getColumn() == position.getColumn() && node.getRow() == position.getRow());
        }

        //az előtte levő enemyt nem támadhatja meg
        for( MyPositionType position : enemyCirclePosition){
            if(actualPosition.getColumn().equals(position.getColumn()) && position.getRow().equals(actualPosition.getRow()-1)){
                selectablePosition.removeIf(node -> node.getColumn() == position.getColumn() && node.getRow() == position.getRow());
            } else if (position.getColumn().equals(actualPosition.getColumn()-1) && position.getRow().equals(actualPosition.getRow()-1)) {
                selectableEnemyPosition.add(position);
            } else if (actualPosition.getColumn()+1==position.getColumn() && actualPosition.getRow()-1 == position.getRow()) {
                selectableEnemyPosition.add(position);
            }
        }

        for (MyPositionType position : circlePosition) {
            selectablePosition.removeIf( node -> node.getColumn() == position.getColumn() && node.getRow() == position.getRow());
        }

        System.out.println("SelectablePositions:"+selectablePosition);
        System.out.println("SelectableEnemyPosition:"+selectableEnemyPosition);


        createSquare();
        //ha a selectablePosition-ben van enemyCircle
        createRedCircleOnSquare();

        //goal_test();


    }

    public void createSquare() {
        for (MyPositionType position : selectablePosition) {
            var square = new Pane();
            square.getStyleClass().add("square");
            square.setOnMouseClicked(this::handleSquareMouseClick);
            grid.add(square, position.getColumn(), position.getRow());
        }
    }

    private void createRedCircleOnSquare() {
        for (MyPositionType position : selectableEnemyPosition){
            createRedCircle(position.getColumn(), position.getRow());
        }
    }
    private void handleSquareMouseClick(MouseEvent event) {
        isClicked=true;

        var circle = (Node) event.getSource();
        var column = GridPane.getColumnIndex(circle);
        var row = GridPane.getRowIndex(circle);

        System.out.println("Selected square: "+new MyPositionType(column, row));

        for (int i=0;i<circlePosition.size();i++){
            if(circlePosition.get(i).getColumn()==actualPosition.getColumn() && circlePosition.get(i).getRow()==actualPosition.getRow()){
                circlePosition.get(i).setColumn(column);
                circlePosition.get(i).setRow(row);
            }
        }

        System.out.println("EnemyCirclePosition: " + enemyCirclePosition);


        if(selectableEnemyPosition.size()==2){
            for (MyPositionType myPositionType : selectableEnemyPosition){
                if (!myPositionType.getColumn().equals(column)){
                    pozicio=myPositionType;
                }
            }
        }


        removeSquare();
        removeChildren(actualPosition);

        if(pozicio!=null){
            enemyCirclePosition.add(pozicio);
            createRedCircle(pozicio.getColumn(), pozicio.getRow());
            pozicio=null;
        }

        actualPosition=new MyPositionType(column,row);



        createBlueCircle(column, row);
        selectableEnemyPosition.clear();
        
        switchPlayers();
    }

    private void switchPlayers() {
    }


    public void removeSquare() {
            System.out.println("SelectablePosition before clear: " + selectablePosition);
            for (MyPositionType position : selectablePosition) {
                removeChildren(position);
            }
            selectablePosition.clear();
            System.out.println("SelectablePosition after clear: " + selectablePosition);

    }
    public void removeChildren(MyPositionType myPositionType){
        for (int i=0;i<enemyCirclePosition.size();i++){
            if(enemyCirclePosition.get(i).getColumn()==myPositionType.getColumn() && enemyCirclePosition.get(i).getRow()==myPositionType.getRow()){
                System.out.println("Removed item from enemyCirclePosition: "+enemyCirclePosition.get(i));
                enemyCirclePosition.remove(i);
            }
        }
        grid.getChildren().removeIf( node ->GridPane.getColumnIndex(node) == myPositionType.getColumn() && GridPane.getRowIndex(node) == myPositionType.getRow());
    }


    //
    //
    //
    //
    public void bugfix(){
        for (int i=0;i<selectablePosition.size();i++){
            int finalI = i;
            grid.getChildren().removeIf(node ->GridPane.getColumnIndex(node) == selectablePosition.get(finalI).getColumn() && GridPane.getRowIndex(node) == selectablePosition.get(finalI).getRow());
            }
        for(MyPositionType positionType:selectableEnemyPosition){createRedCircle(positionType.getColumn(), positionType.getRow());}
        selectablePosition.clear();selectableEnemyPosition.clear();
        }

}



