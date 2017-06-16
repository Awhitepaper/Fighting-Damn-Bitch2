package tw.edu.ntut.csie.game.OOPlab;

import java.util.ArrayList;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;

import static tw.edu.ntut.csie.game.Game.GAME_FRAME_HEIGHT;
import static tw.edu.ntut.csie.game.Game.GAME_FRAME_WIDTH;

public class GameMap implements GameObject {
    public int positionX = 0, positionY = 0; /*地圖貼圖座標(螢幕座標)*/
    private int _sizeBlockX, _sizeBlockY; /*地圖分割格數*/
    private boolean _visible;
    public static final int MAP_WIDTH_PER_BLOCK = 50;
    public static final int MAP_HEIGHT_PER_BLOCK = 50;
    public static final int LITTLE_MAP_WIDTH_PER_BLOCK = 8;
    public static final int LITTLE_MAP_HEIGHT_PER_BLOCK = 8;
    private int[][][] _mapInArray;
    private int _width, _height;

    /**小地圖工具*/
    public static final int littleMapX0 = 10, littleMapY0 = 10;
    private MovingBitmap _littleMapObstacle;
    private MovingBitmap _littleMapFree;
    /**小地圖工具*/
    private ArrayList<ArrayList<ArrayList<MovingBitmap>>> _mapMaterials;
    private ArrayList<ArrayList<MovingBitmap>> _mapMaterialsW, _mapMaterialsJ, _mapMaterialsO;
    private ArrayList<MovingBitmap> _grass, _carpet, _street, _tatami, _desert, _snow, _mount, _oak, _palm, _snowMount, _sea, _spruce;
    private MovingBitmap
/**需做成列舉enum*/
            /*可行走 (   0~ 999)*/ /**_grass, _tile, _stoneTile,<<未編碼*/
                          /* 100*/ _grass0,_grass1,_grass2,_grass3,_grass4,_grass5,_grass6,
                                   _grass7,_grass8,_grass9,_grass11,_grass13,_grass17,_grass19,
                          /* 200*/ _carpet0,_carpet1,_carpet2,_carpet3,_carpet4,_carpet5,_carpet6,
                                   _carpet7,_carpet8,_carpet9,_carpet11,_carpet13,_carpet17,_carpet19,
                          /* 300*/ _street0,_street1,_street2,_street3,_street4,_street5,_street6,
                                   _street7,_street8,_street9,_street11_street13_street17_street19,
                          /* 400*/ _tatami0,_tatami1,_tatami2,_tatami3,_tatami4,_tatami5,_tatami6,
                                   _tatami7,_tatami8,_tatami9,_tatami11,_tatami13,_tatami17,_tatami19,
                          /* 500*/ _desert0,_desert1,_desert2,_desert3,_desert4,_desert5,_desert6,
                                   _desert7,_desert8,_desert9,_desert11,_desert13,_desert17,_desert19,
                          /* 600*/ _snow0,_snow1,_snow2,_snow3,_snow4,_snow5,_snow6,
                                   _snow7,_snow8,_snow9,_snow11,_snow13,_snow17,_snow19,
            /*可跳越 (1000~1999)*/ /**_magma,<<未編碼*/

            /*可飛越 (2000~2999)*/ /**_greenTree, _redTree, _tallGreenTree, _bamboo,<<未編碼*/
                          /*2000*/ _mount0,_mount1,_mount2,_mount3,_mount4,_mount5,_mount6,
                                   _mount7,_mount8,_mount9,_mount11,_mount13,_mount17,_mount19,
                          /*2100*/ _oak0,_oak1,_oak2,_oak3,_oak4,_oak5,_oak6,
                                   _oak7,_oak8,_oak9,_oak11,_oak13,_oak17,_oak19,
                          /*2200*/ _palm0,_palm1,_palm2,_palm3,_palm4,_palm5,_palm6,
                                   _palm7,_palm8,_palm9,_palm11,_palm13,_palm17,_palm19,
                          /*2300*/ _snowMount0,_snowMount1,_snowMount2,_snowMount3,_snowMount4,_snowMount5,_snowMount6,
                                   _snowMount7,_snowMount8,_snowMount9,_snowMount11,_snowMount13,_snowMount17,_snowMount19,
                                   _portal,
                          /*2400*/ _sea0,_sea1,_sea2,_sea3,_sea4,_sea5,_sea6,_sea7,
                                   _sea8,_sea9,_sea11,_sea13,_sea17,_sea19,
                          /*2500*/ _spruce0,_spruce1,_spruce2,_spruce3,_spruce4,_spruce5,_spruce6,
                                   _spruce7,_spruce8,_spruce9,_spruce11,_spruce13,_spruce17,_spruce19;
//    private enum mapMaterial {grass, tile, stoneTile, magma, greenTree, redTree, tallGreenTree, bamboo};

    //constructor without initial position
    public GameMap(int size_of_blocks_X, int size_of_blocks_Y, int[][][] digital_map) {
        _sizeBlockX = size_of_blocks_X;
        _sizeBlockY = size_of_blocks_Y;
        _width = _sizeBlockX*MAP_WIDTH_PER_BLOCK;
        _height = _sizeBlockY*MAP_HEIGHT_PER_BLOCK;
        _mapInArray = new int[2][size_of_blocks_Y][size_of_blocks_X];
        _mapInArray = digital_map;
    }

    public void initialize(int initial_position_X, int initial_position_Y, boolean visible) {
        positionX = initial_position_X;
        positionY = initial_position_Y;
        _visible = visible;

        /**小地圖工具*/
        _littleMapObstacle = new MovingBitmap(R.drawable.little_map_obstacle);
        _littleMapFree = new MovingBitmap(R.drawable.little_map_free);
        _littleMapObstacle.resize(LITTLE_MAP_WIDTH_PER_BLOCK, LITTLE_MAP_HEIGHT_PER_BLOCK);
        _littleMapFree.resize(LITTLE_MAP_WIDTH_PER_BLOCK, LITTLE_MAP_HEIGHT_PER_BLOCK);
        /**小地圖工具*/

        _portal = new MovingBitmap(R.drawable.door);
        _portal.resize(50, 100);

        _grass0 = new MovingBitmap(R.drawable.grass0);
        _grass1 = new MovingBitmap(R.drawable.grass1);
        _grass2 = new MovingBitmap(R.drawable.grass2);
        _grass3 = new MovingBitmap(R.drawable.grass3);
        _grass4 = new MovingBitmap(R.drawable.grass4);
        _grass5 = new MovingBitmap(R.drawable.grass5);
        _grass6 = new MovingBitmap(R.drawable.grass6);
        _grass7 = new MovingBitmap(R.drawable.grass7);
        _grass8 = new MovingBitmap(R.drawable.grass8);
        _grass9 = new MovingBitmap(R.drawable.grass9);
        _grass = new ArrayList<>();
        _grass.add(_grass0);
        _grass.add(_grass1);
        _grass.add(_grass2);
        _grass.add(_grass3);
        _grass.add(_grass4);
        _grass.add(_grass5);
        _grass.add(_grass6);
        _grass.add(_grass7);
        _grass.add(_grass8);
        _grass.add(_grass9);

        _carpet0 = new MovingBitmap(R.drawable.carpet0);
        _carpet1 = new MovingBitmap(R.drawable.carpet1);
        _carpet2 = new MovingBitmap(R.drawable.carpet2);
        _carpet3 = new MovingBitmap(R.drawable.carpet3);
        _carpet4 = new MovingBitmap(R.drawable.carpet4);
        _carpet5 = new MovingBitmap(R.drawable.carpet5);
        _carpet6 = new MovingBitmap(R.drawable.carpet6);
        _carpet7 = new MovingBitmap(R.drawable.carpet7);
        _carpet8 = new MovingBitmap(R.drawable.carpet8);
        _carpet9 = new MovingBitmap(R.drawable.carpet9);
        _carpet = new ArrayList<>();
        _carpet.add(_carpet0);
        _carpet.add(_carpet1);
        _carpet.add(_carpet2);
        _carpet.add(_carpet3);
        _carpet.add(_carpet4);
        _carpet.add(_carpet5);
        _carpet.add(_carpet6);
        _carpet.add(_carpet7);
        _carpet.add(_carpet8);
        _carpet.add(_carpet9);

        _street0 = new MovingBitmap(R.drawable.street0);
        _street1 = new MovingBitmap(R.drawable.street1);
        _street2 = new MovingBitmap(R.drawable.street2);
        _street3 = new MovingBitmap(R.drawable.street3);
        _street4 = new MovingBitmap(R.drawable.street4);
        _street5 = new MovingBitmap(R.drawable.street5);
        _street6 = new MovingBitmap(R.drawable.street6);
        _street7 = new MovingBitmap(R.drawable.street7);
        _street8 = new MovingBitmap(R.drawable.street8);
        _street9 = new MovingBitmap(R.drawable.street9);
        _street = new ArrayList<>();
        _street.add(_street0);
        _street.add(_street1);
        _street.add(_street2);
        _street.add(_street3);
        _street.add(_street4);
        _street.add(_street5);
        _street.add(_street6);
        _street.add(_street7);
        _street.add(_street8);
        _street.add(_street9);

        _tatami0 = new MovingBitmap(R.drawable.tatami0);
        _tatami1 = new MovingBitmap(R.drawable.tatami1);
        _tatami2 = new MovingBitmap(R.drawable.tatami2);
        _tatami3 = new MovingBitmap(R.drawable.tatami3);
        _tatami4 = new MovingBitmap(R.drawable.tatami4);
        _tatami5 = new MovingBitmap(R.drawable.tatami5);
        _tatami6 = new MovingBitmap(R.drawable.tatami6);
        _tatami7 = new MovingBitmap(R.drawable.tatami7);
        _tatami8 = new MovingBitmap(R.drawable.tatami8);
        _tatami9 = new MovingBitmap(R.drawable.tatami9);
        _tatami = new ArrayList<>();
        _tatami.add(_tatami0);
        _tatami.add(_tatami1);
        _tatami.add(_tatami2);
        _tatami.add(_tatami3);
        _tatami.add(_tatami4);
        _tatami.add(_tatami5);
        _tatami.add(_tatami6);
        _tatami.add(_tatami7);
        _tatami.add(_tatami8);
        _tatami.add(_tatami9);

        _desert0 = new MovingBitmap(R.drawable.desert0);
        _desert1 = new MovingBitmap(R.drawable.desert1);
        _desert2 = new MovingBitmap(R.drawable.desert2);
        _desert3 = new MovingBitmap(R.drawable.desert3);
        _desert4 = new MovingBitmap(R.drawable.desert4);
        _desert5 = new MovingBitmap(R.drawable.desert5);
        _desert6 = new MovingBitmap(R.drawable.desert6);
        _desert7 = new MovingBitmap(R.drawable.desert7);
        _desert8 = new MovingBitmap(R.drawable.desert8);
        _desert9 = new MovingBitmap(R.drawable.desert9);
        _desert = new ArrayList<>();
        _desert.add(_desert0);
        _desert.add(_desert1);
        _desert.add(_desert2);
        _desert.add(_desert3);
        _desert.add(_desert4);
        _desert.add(_desert5);
        _desert.add(_desert6);
        _desert.add(_desert7);
        _desert.add(_desert8);
        _desert.add(_desert9);

        _snow0 = new MovingBitmap(R.drawable.snow0);
        _snow1 = new MovingBitmap(R.drawable.snow1);
        _snow2 = new MovingBitmap(R.drawable.snow2);
        _snow3 = new MovingBitmap(R.drawable.snow3);
        _snow4 = new MovingBitmap(R.drawable.snow4);
        _snow5 = new MovingBitmap(R.drawable.snow5);
        _snow6 = new MovingBitmap(R.drawable.snow6);
        _snow7 = new MovingBitmap(R.drawable.snow7);
        _snow8 = new MovingBitmap(R.drawable.snow8);
        _snow9 = new MovingBitmap(R.drawable.snow9);
        _snow = new ArrayList<>();
        _snow.add(_snow0);
        _snow.add(_snow1);
        _snow.add(_snow2);
        _snow.add(_snow3);
        _snow.add(_snow4);
        _snow.add(_snow5);
        _snow.add(_snow6);
        _snow.add(_snow7);
        _snow.add(_snow8);
        _snow.add(_snow9);

/*resize 4 5 8*/
        _mount0 = new MovingBitmap(R.drawable.mount0);
        _mount1 = new MovingBitmap(R.drawable.mount1);
        _mount2 = new MovingBitmap(R.drawable.mount2);
        _mount3 = new MovingBitmap(R.drawable.mount3);
        _mount4 = new MovingBitmap(R.drawable.mount4);
        _mount5 = new MovingBitmap(R.drawable.mount5);
        _mount6 = new MovingBitmap(R.drawable.mount6);
        _mount7 = new MovingBitmap(R.drawable.mount7);
        _mount8 = new MovingBitmap(R.drawable.mount8);
        _mount9 = new MovingBitmap(R.drawable.mount9);
        _mount = new ArrayList<>();
        _mount.add(_mount0);
        _mount.add(_mount1);
        _mount.add(_mount2);
        _mount.add(_mount3);
        _mount.add(_mount4);
        _mount.add(_mount5);
        _mount.add(_mount6);
        _mount.add(_mount7);
        _mount.add(_mount8);
        _mount.add(_mount9);

        _oak0 = new MovingBitmap(R.drawable.oak0);
        _oak1 = new MovingBitmap(R.drawable.oak1);
        _oak2 = new MovingBitmap(R.drawable.oak2);
        _oak3 = new MovingBitmap(R.drawable.oak3);
        _oak4 = new MovingBitmap(R.drawable.oak4);
        _oak5 = new MovingBitmap(R.drawable.oak5);
        _oak6 = new MovingBitmap(R.drawable.oak6);
        _oak7 = new MovingBitmap(R.drawable.oak7);
        _oak8 = new MovingBitmap(R.drawable.oak8);
        _oak9 = new MovingBitmap(R.drawable.oak9);
        _oak = new ArrayList<>();
        _oak.add(_oak0);
        _oak.add(_oak1);
        _oak.add(_oak2);
        _oak.add(_oak3);
        _oak.add(_oak4);
        _oak.add(_oak5);
        _oak.add(_oak6);
        _oak.add(_oak7);
        _oak.add(_oak8);
        _oak.add(_oak9);

        _palm0 = new MovingBitmap(R.drawable.palm0);
        _palm1 = new MovingBitmap(R.drawable.palm1);
        _palm2 = new MovingBitmap(R.drawable.palm2);
        _palm3 = new MovingBitmap(R.drawable.palm3);
        _palm4 = new MovingBitmap(R.drawable.palm4);
        _palm5 = new MovingBitmap(R.drawable.palm5);
        _palm6 = new MovingBitmap(R.drawable.palm6);
        _palm7 = new MovingBitmap(R.drawable.palm7);
        _palm8 = new MovingBitmap(R.drawable.palm8);
        _palm9 = new MovingBitmap(R.drawable.palm9);
        _palm = new ArrayList<>();
        _palm.add(_palm0);
        _palm.add(_palm1);
        _palm.add(_palm2);
        _palm.add(_palm3);
        _palm.add(_palm4);
        _palm.add(_palm5);
        _palm.add(_palm6);
        _palm.add(_palm7);
        _palm.add(_palm8);
        _palm.add(_palm9);

        _snowMount0 = new MovingBitmap(R.drawable.snow_mount0);
        _snowMount1 = new MovingBitmap(R.drawable.snow_mount1);
        _snowMount2 = new MovingBitmap(R.drawable.snow_mount2);
        _snowMount3 = new MovingBitmap(R.drawable.snow_mount3);
        _snowMount4 = new MovingBitmap(R.drawable.snow_mount4);
        _snowMount5 = new MovingBitmap(R.drawable.snow_mount5);
        _snowMount6 = new MovingBitmap(R.drawable.snow_mount6);
        _snowMount7 = new MovingBitmap(R.drawable.snow_mount7);
        _snowMount8 = new MovingBitmap(R.drawable.snow_mount8);
        _snowMount9 = new MovingBitmap(R.drawable.snow_mount9);
        _snowMount = new ArrayList<>();
        _snowMount.add(_snowMount0);
        _snowMount.add(_snowMount1);
        _snowMount.add(_snowMount2);
        _snowMount.add(_snowMount3);
        _snowMount.add(_snowMount4);
        _snowMount.add(_snowMount5);
        _snowMount.add(_snowMount6);
        _snowMount.add(_snowMount7);
        _snowMount.add(_snowMount8);
        _snowMount.add(_snowMount9);

        _sea0 = new MovingBitmap(R.drawable.sea0);
        _sea1 = new MovingBitmap(R.drawable.sea1);
        _sea2 = new MovingBitmap(R.drawable.sea2);
        _sea3 = new MovingBitmap(R.drawable.sea3);
        _sea4 = new MovingBitmap(R.drawable.sea4);
        _sea5 = new MovingBitmap(R.drawable.sea5);
        _sea6 = new MovingBitmap(R.drawable.sea6);
        _sea7 = new MovingBitmap(R.drawable.sea7);
        _sea8 = new MovingBitmap(R.drawable.sea8);
        _sea9 = new MovingBitmap(R.drawable.sea9);
        _sea = new ArrayList<>();
        _sea.add(_sea0);
        _sea.add(_sea1);
        _sea.add(_sea2);
        _sea.add(_sea3);
        _sea.add(_sea4);
        _sea.add(_sea5);
        _sea.add(_sea6);
        _sea.add(_sea7);
        _sea.add(_sea8);
        _sea.add(_sea9);

        _spruce0 = new MovingBitmap(R.drawable.spruce0);
        _spruce1 = new MovingBitmap(R.drawable.spruce1);
        _spruce2 = new MovingBitmap(R.drawable.spruce2);
        _spruce3 = new MovingBitmap(R.drawable.spruce3);
        _spruce4 = new MovingBitmap(R.drawable.spruce4);
        _spruce5 = new MovingBitmap(R.drawable.spruce5);
        _spruce6 = new MovingBitmap(R.drawable.spruce6);
        _spruce7 = new MovingBitmap(R.drawable.spruce7);
        _spruce8 = new MovingBitmap(R.drawable.spruce8);
        _spruce9 = new MovingBitmap(R.drawable.spruce9);
        _spruce = new ArrayList<>();
        _spruce.add(_spruce0);
        _spruce.add(_spruce1);
        _spruce.add(_spruce2);
        _spruce.add(_spruce3);
        _spruce.add(_spruce4);
        _spruce.add(_spruce5);
        _spruce.add(_spruce6);
        _spruce.add(_spruce7);
        _spruce.add(_spruce8);
        _spruce.add(_spruce9);

        //ArrayList relationship create
        _mapMaterialsW = new ArrayList<>();
        _mapMaterialsW.add(new ArrayList<MovingBitmap>());
        _mapMaterialsW.add(_grass);
        _mapMaterialsW.add(_carpet);
        _mapMaterialsW.add(_street);
        _mapMaterialsW.add(_tatami);
        _mapMaterialsW.add(_desert);
        _mapMaterialsW.add(_snow);
        _mapMaterialsJ = new ArrayList<>();
        _mapMaterialsO = new ArrayList<>();
        _mapMaterialsO.add(_mount);
        _mapMaterialsO.add(_oak);
        _mapMaterialsO.add(_palm);
        _mapMaterialsO.add(_snowMount);
        _mapMaterialsO.add(_sea);
        _mapMaterialsO.add(_spruce);

        _mapMaterials = new ArrayList<>();
        _mapMaterials.add(_mapMaterialsW);
        _mapMaterials.add(_mapMaterialsJ);
        _mapMaterials.add(_mapMaterialsO);

        //Unity materials' size
        for(ArrayList<ArrayList<MovingBitmap>> temp : _mapMaterials) {
            for (ArrayList<MovingBitmap> temp2 : temp) {
                for (MovingBitmap temp3 : temp2){
                    temp3.resize(MAP_WIDTH_PER_BLOCK, MAP_HEIGHT_PER_BLOCK);
                }
            }
        }
    }

    @Override
    public void release() {
        /**小地圖工具*/
        _littleMapObstacle.release();
        _littleMapObstacle = null;
        _littleMapFree.release();
        _littleMapFree = null;
        /**小地圖工具*/


        _portal.release();
        _portal = null;

        for(ArrayList<ArrayList<MovingBitmap>> temp : _mapMaterials) {
            for (ArrayList<MovingBitmap> temp2 : temp) {
                for (MovingBitmap temp3 : temp2){
                    temp3.release();
                }
            }
        }
        for(ArrayList<ArrayList<MovingBitmap>> temp : _mapMaterials) {
            for (ArrayList<MovingBitmap> temp2 : temp) {
                temp2.clear();
            }
        }
        for(ArrayList<ArrayList<MovingBitmap>> temp : _mapMaterials) {
            temp.clear();
        }

        _mapMaterials.clear();
        _mapMaterials = null;
    }

    @Override
    public void move() {}

    @Override
    public void show() {
        if (_visible) {
            for (int z = 0; z < 2; z++) {
                for (int x = 0; x < _sizeBlockX; x++) {
                    for (int y = 0; y < _sizeBlockY; y++) {
                        int X = positionX + (MAP_WIDTH_PER_BLOCK * x);
                        int Y = positionY + (MAP_HEIGHT_PER_BLOCK * y);
                        if (_mapInArray[z][y][x] < 0) {
                            //傳送門圖式
//                            _portal.setLocation(X, Y-50);
//                            _portal.show();
                        } else {
                            int foo1 = _mapInArray[z][y][x] / 1000;
                            int foo2 = (_mapInArray[z][y][x] % 1000) / 100;
                            int foo3 = _mapInArray[z][y][x] % 100;
                            if (foo1 == 0 && foo2 == 0)
                                continue;
                            _mapMaterials.get(foo1).get(foo2).get(foo3).show(X, Y);
                        }

                    }
                }
            }
        }
    }

    public void LittleMapShow(ArrayList<Creature> cs) {
        if (_visible) {
            for (int x = 0, lsx = littleMapX0; x < _width; x += MAP_WIDTH_PER_BLOCK, lsx += LITTLE_MAP_WIDTH_PER_BLOCK) {
                for (int y = 0, lsy = littleMapY0; y < _height; y += MAP_HEIGHT_PER_BLOCK, lsy += LITTLE_MAP_HEIGHT_PER_BLOCK) {
                    if (this.IsEmpty(x, y)) {
                        _littleMapFree.show(lsx, lsy);
                    } else {
                        _littleMapObstacle.show(lsx, lsy);
                    }
                }
            }
            //show creatures littleMapIcon
            /**小地圖工具*/
            for (Creature creatures : cs) {
                creatures.showLittleMapIcon();
            }
        }
    }


    public boolean IsEmpty(int x, int y) { /**移動mode ，飛、走、跳*/
        int gx = x / MAP_WIDTH_PER_BLOCK;
        int gy = y / MAP_HEIGHT_PER_BLOCK;
        return _mapInArray[1][gy][gx] < 1000 ;//&& _mapInArray[1][gy][gx] >= 0;
    }

//    提供英雄地圖座標來決定地圖螢幕座標
    public void setScreenCoordinateX(int hero_mpx) {
        positionX = (GAME_FRAME_WIDTH - Hero.heroPictureWidth)/2 - hero_mpx;
    }
    public void setScreenCoordinateY(int hero_mpy) {
        positionY = (GAME_FRAME_HEIGHT - Hero.heroPictureHeight)/2 - hero_mpy;
    }


    /**
     *傳送點偵查
     *
     * @param x
     * @param y
     * @return 踩到的傳送點代碼 或 0(沒有踩到傳送點)
     */
    public int portalEvenHandler(int x, int y) {
        int gx = x / MAP_WIDTH_PER_BLOCK;
        int gy = y / MAP_HEIGHT_PER_BLOCK;
        if (_mapInArray[1][gy][gx] < 0) {
            return -_mapInArray[1][gy][gx];
        }
        return 0;
    }

    public void setVisible(boolean visible) {
        _visible = visible;
    }
    public boolean checkVisible() {
        return _visible;
    }

    public int getWidth() {
        return _width;
    }
    public int getHeight() {
        return _height;
    }


}


