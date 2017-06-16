package tw.edu.ntut.csie.game.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import tw.edu.ntut.csie.game.OOPlab.AttackButton;
import tw.edu.ntut.csie.game.OOPlab.Creature;
import tw.edu.ntut.csie.game.OOPlab.DebugerTools;
import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.OOPlab.Joystick;
import tw.edu.ntut.csie.game.OOPlab.GameMap;
import tw.edu.ntut.csie.game.OOPlab.Hero;
import tw.edu.ntut.csie.game.OOPlab.HeroInformation;
import tw.edu.ntut.csie.game.OOPlab.Monsters.BossSlime;
import tw.edu.ntut.csie.game.OOPlab.Monsters.DemoSlime;
import tw.edu.ntut.csie.game.OOPlab.Monsters.Monster;
import tw.edu.ntut.csie.game.OOPlab.Monsters.MonsterSlime;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.OOPlab.Monsters.RedSlime;
import tw.edu.ntut.csie.game.OOPlab.SkillButton;
import tw.edu.ntut.csie.game.core.Audio;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.Animation;


public class StateRun extends GameState {
    //物件
    private Joystick _joystick;
    private GameMap _map0,_map1,_map2,_map3;
    private ArrayList<GameMap> _maps;
    private Hero _hero;
    private HeroInformation _heroInformation;//Management of status and backpack;
    private MonsterSlime _monsterSlime, _monsterSlime1,_monsterSlime2, _monsterSlime3, _monsterSlime4;
    private RedSlime _redSlime, _redSlime1, _redSlime2, _redSlime3, _redSlime4;
    private DemoSlime _demoSlime, _demoSlime1, _demoSlime2;
    private BossSlime _bossSlime;
    private ArrayList<Creature> _creatures;
    private ArrayList<Monster> _monsters;
    private AttackButton _attackButton;
    private SkillButton _skillButton;
    private Animation _loseAnimation;
    private Audio _music;
    //private StateOver _stateOver;
    private int[][][] digitalMap_Map0 =
                          {{{   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
                            {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0}},

                           {{2000,2000,2000,2000,2000,2000,2000,2100,2100,2100,2100,2100,2100,2100,   0,   0,   0,   0,   0,   0},
                            {2000,2001,2002,2003, 200, 201, 202, 202, 203, 200,2101,2102,2103,2100,   0,   0,   0,   0,   0,   0},
                            {2000,2004,2005,2006, 200, 204, 205, 205, 206, 200,2104,2105,2106,2100,   0,   0,   0,   0,   0,   0},
                            {2000,2007,2008,2009, 200, 207, 208, 208, 209, 200,2107,2108,2109,2100,   0,   0,   0,   0,   0,   0},
                            {2000, 100, 100, 100, 500, 500, 500, 600, 600, 600, 300, 300, 300,2100,   0,   0,   0,   0,   0,   0},
                            {2000, 101, 102, 103, 501, 502, 503, 601, 602, 603, 301, 302, 303,2100,   0,   0,   0,   0,   0,   0},
                            {- 12, 104, 105, 106, 504, 505, 506, 604, 605, 606, 304, 305, 306,2100,   0,   0,   0,   0,   0,   0},
                            {- 12, 104, 105, 106, 504, 505, 506, 604, 605, 606, 304, 305, 306,2300,   0,   0,   0,   0,   0,   0},
                            {2200, 107, 108, 109, 507, 508, 509, 607, 608, 609, 307, 308, 309,2300,   0,   0,   0,   0,   0,   0},
                            {2200, 100, 100, 100, 500, 500, 500, 600, 600, 600, 300, 300, 300,2300,   0,   0,   0,   0,   0,   0},
                            {2200,2201,2202,2203, 400, 401, 402, 402, 403, 400,2301,2302,2303,2300,   0,   0,   0,   0,   0,   0},
                            {2200,2204,2205,2206, 400, 404, 405, 405, 406, 400,2304,2305,2306,2300,   0,   0,   0,   0,   0,   0},
                            {2200,2207,2208,2209, 400, 407, 408, 408, 409, 400,2307,2308,2309,2300,   0,   0,   0,   0,   0,   0},
                            {2200,2200,2200,2200,2200,2200,2200,2300,2300,2300,2300,2300,2300,2300,   0,   0,   0,   0,   0,   0}}};

    private int[][][] digitalMap_Map1 =
                          {{{ 101, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 103},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 104, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 106},
                            { 107, 108, 108, 108, 108, 108, 108, 108, 108, 108 ,108, 108, 108, 108, 108, 108 ,108, 108, 108, 109}},

                           {{2007,2008,2008,2008,2008,2008,2008,2008,2008,2008,2008,2008,2008,2009,2104,2105,2105,2105,2105,2105},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2100,2104,2105,2105,2105,2105,2105},
                            {2006,   0,   0,   0,   0,   0,2001,2003,2000,   0,   0,   0,   0,   0,2104,2105,2105,2105,2105,2105},
                            {2006,   0,   0,   0,   0,   0,2007,2009,   0,   0,   0,   0,   0,   0,2104,2105,2105,2105,2105,2105},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2100,   0,   0,2104,2105,2105,2105,2105,2105},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2107,2108,2108,2108,2108,2108},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,-103},//傳送點代碼 <-><當前地圖><前往地圖><方向>
                            {2006,   0,   0,   0,   0,2100,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,-103},
                            {2009,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2100,   0,   0,2001},
                            {2105,2102,2102,2102,2103,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2105,2105,2105,2105,2106,   0,   0,   0,   0,   0,   0,   0,   0,2100,   0,   0,   0,   0,   0,2004},
                            {2105,2105,2105,2105,2106,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2105,2105,2105,2105,2106,2100,   0,-121,-121,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2105,2105,2105,2105,2106,2001,2003,-121,-121,2001,2002,2002,2002,2002,2002,2002,2002,2002,2002,2003}}};

    private int[][][] digitalMap_Map2 =
                          {{{ 501, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 503},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 504, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 505, 506},
                            { 509, 508, 508, 508, 508, 508, 508, 508, 508, 508 ,508, 508, 508, 508, 508, 508 ,508, 508, 508, 509}},

                           {{2007,2008,2008,2008,2008,2008,2009,-210,-210,2007,2008,2008,2008,2008,2008,2008,2008,2008,2008,2009},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2006,   0,   0,2001,2003,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2006,   0,   0,2007,2009,   0,   0,   0,   0,2200,2200,2200,2200,2200,2200,   0,   0,   0,   0,2004},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,2200,2401,2402,2402,2403,2200,   0,   0,   0,   0,2004},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,2200,2404,2405,2405,2406,2200,   0,   0,   0,   0,2004},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,2200,2404,2405,2405,2406,2200,   0,   0,   0,   0,2004},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,2200,2407,2408,2408,2409,2200,   0,   0,   0,   0,2004},
                            {2006,   0,   0,   0,   0,   0,   0,   0,   0,2200,2200,2200,2200,2200,2200,   0,   0,   0,   0,2004},
                            {2009,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2402,2402,2403,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2405,2405,2406,2200,   0,   0,   0,   0,   0,-231,-231,   0,   0,   0,   0,   0,   0,   0,   0,2004},
                            {2405,2405,2406,2201,2202,2202,2202,2202,2203,-231,-231,2001,2002,2002,2002,2002,2002,2002,2002,2003}}};

    private int[][][] digitalMap_Map3 =
                          {{{ 601, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 602, 603},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 604, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 605, 606},
                            { 607, 608, 608, 608, 608, 608, 608, 608, 608, 608 ,608, 608, 608, 608, 608, 608 ,608, 608, 608, 609}},

                           {{2307,2308,2308,2308,2308,2308,2308,2308,2309,-320,-320,2307,2308,2308,2308,2308,2308,2308,2308,2309},
                            {2306,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,2500,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,2300,   0,   0,   0,2500,   0,   0,   0,   0,   0,   0,2501,2502,2503,   0,   0,2304},
                            {2306,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2504,2505,2506,   0,   0,2304},
                            {2306,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2507,2508,2509,   0,   0,2304},
                            {2306,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,   0,   0,   0,2500,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,2501,2502,2503,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,2504,2505,2506,   0,   0,   0,   0,   0,   0,2500,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,2507,2508,2509,   0,   0,   0,   0,   0,   0,   0,2500,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2306,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,2304},
                            {2301,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2302,2303}}};

    private DebugerTools _debuger;

    public StateRun(GameEngine engine) {super(engine);}

    @Override
    public void initialize(Map<String, Object> data) {
        _map0 = new GameMap(20, 14, digitalMap_Map0);
        _map0.initialize(0, 0, false);
        _map1 = new GameMap(20, 14, digitalMap_Map1);
        _map1.initialize(-43, -59, true);
        _map2 = new GameMap(20, 14, digitalMap_Map2);
        _map2.initialize(0, 0, false);
        _map3 = new GameMap(20, 14, digitalMap_Map3);
        _map3.initialize(0, 0, false);
        _maps = new ArrayList<>();
        _maps.add(_map0);
        _maps.add(_map1);
        _maps.add(_map2);
        _maps.add(_map3);

        _joystick = new Joystick();
        _joystick.initialize();
        _attackButton = new AttackButton();
        _attackButton.initialize();
        _skillButton = new SkillButton();
        _skillButton.initialize();

        _music = new Audio(R.raw.ntut);
        _music.setRepeating(true);
        //_music.play();

        _hero = new Hero();
        _hero.initialize(_map1);
        _heroInformation = new HeroInformation();
        _heroInformation.initialize(_hero);


        //_stateOver = new StateOver(_engine);
        //_stateOver.initialize(_hero);
        _monsterSlime = new MonsterSlime(_map1);
        _monsterSlime.initialize(225, 125, _hero);
        _monsterSlime1 = new MonsterSlime(_map1);
        _monsterSlime1.initialize(275, 150, _hero);
        _monsterSlime2 = new MonsterSlime(_map1);
        _monsterSlime2.initialize(400, 500, _hero);
        _monsterSlime3 = new MonsterSlime(_map1);
        _monsterSlime3.initialize(450, 500, _hero);
        _monsterSlime4 = new MonsterSlime(_map1);
        _monsterSlime4.initialize(750, 450, _hero);

        _redSlime = new RedSlime(_map2);
        _redSlime.initialize(780, 70, _hero);
        _redSlime1 = new RedSlime(_map2);
        _redSlime1.initialize(200, 370, _hero);
        _redSlime2 = new RedSlime(_map2);
        _redSlime2.initialize(190, 430, _hero);
        _redSlime3 = new RedSlime(_map2);
        _redSlime3.initialize(550, 550, _hero);
        _redSlime4 = new RedSlime(_map2);
        _redSlime4.initialize(800, 550, _hero);

        _bossSlime = new BossSlime(_map3);
        _bossSlime.initialize(500, 355, _hero);

        _demoSlime = new DemoSlime(_map0);
        _demoSlime.initialize(300, 150, _hero);
        _demoSlime1 = new DemoSlime(_map0);
        _demoSlime1.initialize(315, 135, _hero);
        _demoSlime2 = new DemoSlime(_map0);
        _demoSlime2.initialize(330, 120, _hero);

        _creatures = new ArrayList<>();
        _creatures.add(_hero);
        _creatures.add(_monsterSlime);
        _creatures.add(_monsterSlime1);
        _creatures.add(_monsterSlime2);
        _creatures.add(_monsterSlime3);
        _creatures.add(_monsterSlime4);
        _creatures.add(_redSlime);
        _creatures.add(_redSlime1);
        _creatures.add(_redSlime2);
        _creatures.add(_redSlime3);
        _creatures.add(_redSlime4);
        _creatures.add(_bossSlime);
        _creatures.add(_demoSlime);
        _creatures.add(_demoSlime1);
        _creatures.add(_demoSlime2);

        _monsters = new ArrayList<>();
        _monsters.add(_monsterSlime);
        _monsters.add(_monsterSlime1);
        _monsters.add(_monsterSlime2);
        _monsters.add(_monsterSlime3);
        _monsters.add(_monsterSlime4);
        _monsters.add(_redSlime);
        _monsters.add(_redSlime1);
        _monsters.add(_redSlime2);
        _monsters.add(_redSlime3);
        _monsters.add(_redSlime4);
        _monsters.add(_bossSlime);
        _monsters.add(_demoSlime);
        _monsters.add(_demoSlime1);
        _monsters.add(_demoSlime2);

        _loseAnimation = new Animation(3);
        _loseAnimation.setPlay(false);
        _loseAnimation.setRepeating(false);
        _loseAnimation.setLocation(0, 0);
        _loseAnimation.addFrame(R.drawable.lose_animation1);
        _loseAnimation.addFrame(R.drawable.lose_animation2);
        _loseAnimation.addFrame(R.drawable.lose_animation3);
        _loseAnimation.addFrame(R.drawable.lose_animation4);
        _loseAnimation.addFrame(R.drawable.lose_animation5);
        _loseAnimation.addFrame(R.drawable.lose_animation6);
        _loseAnimation.addFrame(R.drawable.lose_animation7);
        _loseAnimation.addFrame(R.drawable.lose_animation8);

        _debuger = new DebugerTools();
        _debuger.initialize(_hero);
    }

    @Override
    public void move() {
        for (Creature creatures : _creatures)
            creatures.move();

        for (GameMap maps : _maps)
            maps.move();

//        移動、傳送處理
        if (!_joystick._released) {
            _hero.OnMove(_joystick.displacementX, _joystick.displacementY);
            int portal_ID = _hero.checkLocatedMap().portalEvenHandler(_hero.checkMpx(), _hero.checkMpy());
            if (portal_ID > 0){
                int currMap = portal_ID/100;
                int nextMap = portal_ID%100/10;
                int dir = portal_ID%10;
                _maps.get(currMap).setVisible(false);
                _hero.OnConvey(_maps.get(nextMap), dir);
                _maps.get(nextMap).setVisible(true);
                for (Monster Ms : _monsters) {
                    if (Ms.checkLocatedMap() == _maps.get(nextMap)) {
                        Ms.initialize(_hero);
                    }
                }
            }
            _hero.OnShow(_joystick.director);
            _hero.movingShow();
        }
        _heroInformation.move();
        _loseAnimation.move();
        _debuger.move();
    }

    @Override
    public void show() {
        if (_bossSlime.checkDieAnimationEnd()){
            changeState(Game.OVER_STATE);
        }
        for (GameMap maps : _maps)
            maps.show();

        //比較mpy 值決定圖層順序後貼圖
        Collections.sort(_creatures, new Comparator<Creature>(){
            @Override
            public int compare(Creature c1, Creature c2) {
                return (c1.checkMpy()+c1.checkHeight()) - (c2.checkMpy()+c2.checkHeight());
            }
        });
        for (Creature creatures : _creatures) {
            creatures.show();
        }

        _joystick.show();
        _attackButton.show();
        _skillButton.show();

        for (Monster monsters : _monsters) {
            monsters.showAttackValue();
        }

        for (GameMap maps : _maps)
            maps.LittleMapShow(_creatures);

        _heroInformation.show();
        _debuger.show();

        //角色死亡畫面
        if(_hero.checkHP() <= 0){
            _loseAnimation.setPlay(true);
            _loseAnimation.show();
            if (_loseAnimation.getCurrentFrameIndex() == -1){
                _loseAnimation.show(_loseAnimation.getFrameCount()-1);
            }
        }
    }

    @Override
    public void release() {
        _music.release();
        _music = null;

        _joystick.release();
        _joystick = null;

        for (GameMap maps : _maps)
            maps.release();
        _maps.clear();
        _maps = null;

        for (Creature creatures : _creatures)
            creatures.release();
        _creatures.clear();
        _creatures = null;

        _monsters.clear();
        _monsters = null;

        _attackButton.release();
        _attackButton = null;
        _skillButton.release();
        _skillButton = null;

        _heroInformation.release();
        _heroInformation = null;

        _loseAnimation.release();
        _loseAnimation = null;

        _debuger.release();
        _debuger = null;
    }

    @Override
    public void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void orientationChanged(float pitch, float azimuth, float roll) {
    }

    @Override
    public void accelerationChanged(float dX, float dY, float dZ) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean pointerPressed(List<Pointer> pointers) {
        if(_hero.checkHP() > 0) {//腳色死亡(lose畫面)暫停所有指令存取
            _joystick.pointerPressed(pointers);

            //attackButton
            if(_attackButton.pointerPressed(pointers) && _hero.checkPlayFinished_attack()) {
                _hero.attackShow();
                for (Monster monsters : _monsters) {
                    _hero.attack(monsters);
                }
            }
            //skillButton
            _heroInformation.pointerPressed(pointers);
            if(_skillButton.pointerPressed(pointers) && _hero.checkPlayFinished_attack()) {
                Collections.sort(_monsters, new Comparator<Monster>(){
                    @Override
                    public int compare(Monster c1, Monster c2) {
                        int dhmC1 = distanceOf(c1, _hero);
                        int dhmC2 = distanceOf(c2, _hero);
                        return (dhmC1 - dhmC2);
                    }
                });
                for (Monster monsters : _monsters) {
                    if(_hero.useSkill(_skillButton.skillPressed, monsters)) {
                        _hero.skillShow(_skillButton.skillPressed, monsters);
                        if (_skillButton.skillPressed == 1){
                            for (Monster ms : _monsters) {
                                if (distanceOf(ms, monsters) <= 50){
                                    _hero.SplashingDMG(ms, distanceOf(ms, monsters));
                                }
                            }
                        }
                        break;
                    }
                }
            }
            _debuger.pointerPressed(pointers);
        } else {
            changeState(Game.INITIAL_STATE);
        }

        return true;
    }
    @Override
    public boolean pointerMoved(List<Pointer> pointers) {
        _joystick.pointerMoved(pointers);
        _heroInformation.pointerMoved(pointers);
        _debuger.pointerMoved(pointers);
        return false;
    }

    @Override
    public boolean pointerReleased(List<Pointer> pointers) {
        if(_joystick.pressed) {
            _joystick.pointerReleased(pointers);
            _hero.stopMoving();
        }
        _heroInformation.pointerReleased(pointers);
        _debuger.pointerReleased(pointers);
        if(pointers.size() == 1 && pointers.get(0).getY() > 90 && pointers.get(0).getY() < 120) {
            if (pointers.get(0).getX() > 10 && pointers.get(0).getX() <= 40)
                _hero.invincible = !_hero.invincible;
            else if (pointers.get(0).getX() > 137 && pointers.get(0).getX() <= 167)
                _hero.superDamage = !_hero.superDamage;
        }

        return false;
    }

    @Override
    public void pause() {
        _music.pause();
    }

    @Override
    public void resume() {
        _music.resume();
    }

    public int distanceOf(Creature c1, Creature c2){
        int distanceX = (c1.checkMpx() + c1.checkWidth()/2) - (c2.checkMpx() + c2.checkWidth()/2);
        int distanceY = (c1.checkMpy() + c1.checkHeight()) - (c2.checkMpy() + c2.checkHeight());
        int distance = (int)Math.sqrt (Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        return  distance;
    }
}
