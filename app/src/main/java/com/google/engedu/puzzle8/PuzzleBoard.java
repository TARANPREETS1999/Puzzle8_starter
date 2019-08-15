/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;
    private int steps;
    private PuzzleBoard previousboard;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        steps=0;
        previousBoard=null;
        tiles=new ArrayList<>();
        Bitmap scaleBitmap=Bitmap.createBitmap(bitmap.parentwidth.parentWidth,false);
        int Width=parentWidth/NUM_TILES;
        for(int y=0;y<NUM_TILES;y++){
            for (int x=0;x<NUM_TILES;x++){
                int num= y*NUM_TILES+x;
            if (num!=NUM_TILES*NUM_TILES-1){
                Bitmap tileBitmap=Bitmap.createBitmap(scaleBitmap,x*width,y*width,width,width);
                PuzzleTile tile=new PuzzleTile(tileBitmap,num);
                tiles.add(tile);
            }else{
                tiles.add(null);
            }
        }
    }

    PuzzleBoard(PuzzleBoard otherBoard,int steps){
            this.steps = steps + 1;
            this.previousboard=otherboards;
            tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        }
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
     0       int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        return null;
    }
    public ArrayList<PuzzleBoard> neighbours()
        {
            ArrayList<PuzzleBoard> neighbours=new ArrayList<>();
            int emptytileX=0;
            int emptytileY=0;

            for(int i=0;i<NUM_TILES*NUM_TILES; i++){
                if(tiles.get(i)==null){
                    emptytileX=i%NUM_TILES;
                    emptytileX=i/NUM_TILES;
                    break;
                }
            }

            for(int [] delta:NEIGHBOUR_COORDS){
                int neighboursX=emptytileX+delta[0];
                int neighboursY=emptytileXY+delta[1];

                if(neighboursX=0 && neighboursX<NUM_TILES && neighboursY>=0 && neighboursY<NUM_TILES){
                    PuzzleBoard neighboursBorad=new PuzzleBoard(otherboard, this,steps);
                    neighboursBorad.swapTiles(neighboursX,neighbourY),XYtoIndex(emptytileX,emptytileY);
                    neighbours.add(neighboursBorad);
                }

            }

        }

    public int priority() {
        int manhattandistance=0;

        for(int i=0;i<NUM_TILES*NUM_TILES;i++){
            PuzzleTile tile=tiles.get(i);
            if(tile!=null){
                int correctPos=tile.getNumber();
                int correctX=correctPos%NUM_TILES;
                int correctY=correctPos/NUM_TILES;
                int currentX=i%NUM_TILES;
                int currentY=i/NUM_TILES;
                manhattandistance=manhattandistance+Math.abs(currentX-currentX)+Math.abs(currentX-currentY);
            }
        }
        return manhattandistance+steps;
    }


    public PuzzleBoard getPreviousboard() {
        return previousboard;
    }

    public void setPreviousboard(PuzzleBoard previousboard) {
        this.previousboard = previousboard;
    }
}
