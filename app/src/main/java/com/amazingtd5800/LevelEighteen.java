package com.amazingtd5800;

import java.util.List;

/** Level 18 (background 1), starts with 300 money. Original: qd. */
final class LevelEighteen extends Level {

    LevelEighteen(Hud hud, int number) {
        super(hud, number, 1);
        // Path from entry point 0, 12 waypoints. Original: dd.
        Path path0 = new Path(12);
        addPath(path0);
        path0.add(new Vec2(snap(GameScreen.WIDTH + 16), snap(164)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 188), snap(76)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 340), snap(187)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 307), snap(236)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 186), snap(150)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 187), snap(387)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 340), snap(477)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 307), snap(534)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 116), snap(426)));
        path0.add(new Vec2(snap(GameScreen.WIDTH - 116), snap(182)));
        path0.add(new Vec2(snap(GameScreen.WIDTH + 16), snap(244)));
        // 10 enemies every 2000 ms, the first after 30000 ms. Original: ih.
        addWave(new Wave(30000, 10, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
            }
        });
        // 20 enemies every 2500 ms, the first after 20000 ms. Original: ig.
        addWave(new Wave(20000, 20, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 20 enemies every 1700 ms, the first after 20000 ms. Original: ij.
        addWave(new Wave(20000, 20, 1700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.SHREDDER, false);
                enemy.addEffect(new Heal());
                waiting.add(enemy);
            }
        });
        // 20 enemies every 1500 ms, the first after 20000 ms. Original: ii.
        addWave(new Wave(20000, 20, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 10 enemies every 4000 ms, the first after 20000 ms. Original: il.
        addWave(new Wave(20000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.VIRUS2, false));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: ik.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE_TWO, false));
            }
        });
        // 26 enemies every 800 ms, the first after 20000 ms. Original: to.
        addWave(new Wave(20000, 26, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
            }
        });
        // 16 enemies every 2600 ms, the first after 20000 ms. Original: tp.
        addWave(new Wave(20000, 16, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE_TWO, true));
            }
        });
        // 14 enemies every 2200 ms, the first after 20000 ms. Original: tq.
        addWave(new Wave(20000, 14, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, true));
            }
        });
        // 30 enemies every 1400 ms, the first after 20000 ms. Original: gy.
        addWave(new Wave(20000, 30, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 16 enemies every 2000 ms, the first after 20000 ms. Original: gw.
        addWave(new Wave(20000, 16, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.WORM_TWO, false));
            }
        });
        // 26 enemies every 3400 ms, the first after 20000 ms. Original: hb.
        addWave(new Wave(20000, 26, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining <= 12) {
                    waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.VIRUS_TWO, false));
            }
        });
        // 12 enemies every 2500 ms, the first after 20000 ms. Original: gz.
        addWave(new Wave(20000, 12, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SPARK, false));
            }
        });
        // 15 enemies every 2000 ms, the first after 20000 ms. Original: hf.
        addWave(new Wave(20000, 15, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, true));
            }
        });
        // 18 enemies every 2200 ms, the first after 20000 ms. Original: hd.
        addWave(new Wave(20000, 18, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE_TWO, false));
            }
        });
        // 10 enemies every 3000 ms, the first after 20000 ms. Original: hj.
        addWave(new Wave(20000, 10, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SPARK_TWO, false));
            }
        });
        // 20 enemies every 1500 ms, the first after 20000 ms. Original: hh.
        addWave(new Wave(20000, 20, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, true));
            }
        });
        // 10 enemies every 2600 ms, the first after 20000 ms. Original: ho.
        addWave(new Wave(20000, 10, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.WORM_TWO, true));
            }
        });
        // 8 enemies every 3600 ms, the first after 20000 ms. Original: hm.
        addWave(new Wave(20000, 8, 3600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK_TWO, false));
            }
        });
        // 3 enemies every 5000 ms, the first after 20000 ms. Original: gg.
        addWave(new Wave(20000, 3, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_BIG, false));
            }
        });
        hud.start(300, waves());
    }
}
