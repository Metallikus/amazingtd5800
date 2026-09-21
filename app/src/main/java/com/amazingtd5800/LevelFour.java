package com.amazingtd5800;

import java.util.List;

/** Level 4 (background 3), starts with 200 money. Original: fx. */
final class LevelFour extends Level {

    LevelFour(Hud hud, int number) {
        super(hud, number, 3);
        // Path from entry point 0, 5 waypoints. Original: dd.
        Path path0 = new Path(5);
        addPath(path0);
        path0.add(new Vec2(snap(160), snap(0)));
        path0.add(new Vec2(snap(160), snap(GameScreen.HEIGHT - 58 - 100)));
        path0.add(new Vec2(snap(240), snap(GameScreen.HEIGHT - 58 - 100)));
        path0.add(new Vec2(snap(240), snap(150)));
        path0.add(new Vec2(snap(0), snap(150)));
        // Path from entry point 1, 5 waypoints. Original: dd.
        Path path1 = new Path(5);
        addPath(path1);
        path1.add(new Vec2(snap(200), snap(GameScreen.HEIGHT - 58)));
        path1.add(new Vec2(snap(200), snap(108)));
        path1.add(new Vec2(snap(120), snap(108)));
        path1.add(new Vec2(snap(120), snap(GameScreen.HEIGHT - 58 - 150)));
        path1.add(new Vec2(snap(360), snap(GameScreen.HEIGHT - 58 - 150)));
        // Upgrade spot (RELOAD). Original: ol.
        spot(snap(168), snap(160), Spot.RELOAD);
        // Upgrade spot (DAMAGE). Original: ol.
        spot(snap(168), snap(272), Spot.DAMAGE);
        // Upgrade spot (RELOAD). Original: ol.
        spot(snap(168), snap(400), Spot.RELOAD);
        // 9 enemies every 2100 ms, the first after 30000 ms. Original: kt.
        addWave(new Wave(30000, 9, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 18 enemies every 2400 ms, the first after 20000 ms. Original: kj.
        addWave(new Wave(20000, 18, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
                waiting.add(spawn(paths[1], EnemyType.BLOB, false));
            }
        });
        // 12 enemies every 2000 ms, the first after 20000 ms. Original: kh.
        addWave(new Wave(20000, 12, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.LINE, false));
                    waiting.add(spawn(paths[1], EnemyType.LINE, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
            }
        });
        // 12 enemies every 5000 ms, the first after 20000 ms. Original: ko.
        addWave(new Wave(20000, 12, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                interval = remaining % 3 == 0 ? 5000 : 1200;
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.SUN_THREE, false));
            }
        });
        // 10 enemies every 2200 ms, the first after 20000 ms. Original: kl.
        addWave(new Wave(20000, 10, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BLOB, true));
                waiting.add(spawn(paths[1], EnemyType.BLOB, true));
            }
        });
        // 18 enemies every 2200 ms, the first after 20000 ms. Original: ht.
        addWave(new Wave(20000, 18, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 10 enemies every 1900 ms, the first after 20000 ms. Original: hs.
        addWave(new Wave(20000, 10, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, true));
                waiting.add(spawn(paths[1], EnemyType.ELLIPSE, true));
            }
        });
        // 10 enemies every 1800 ms, the first after 20000 ms. Original: hv.
        addWave(new Wave(20000, 10, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, true));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, true));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: hu.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
                waiting.add(spawn(paths[1], EnemyType.BALL, false));
            }
        });
        // 30 enemies every 1400 ms, the first after 20000 ms. Original: dn.
        addWave(new Wave(20000, 30, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, true));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, true));
            }
        });
        // 6 enemies every 1900 ms, the first after 20000 ms. Original: do.
        addWave(new Wave(20000, 6, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, true));
                waiting.add(spawn(paths[1], EnemyType.CIRCLE, true));
            }
        });
        // 12 enemies every 1200 ms, the first after 20000 ms. Original: dp.
        addWave(new Wave(20000, 12, 1200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BLOB, true));
                waiting.add(spawn(paths[1], EnemyType.BLOB, true));
            }
        });
        // 20 enemies every 1800 ms, the first after 20000 ms. Original: de.
        addWave(new Wave(20000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
            }
        });
        // 12 enemies every 1700 ms, the first after 20000 ms. Original: dg.
        addWave(new Wave(20000, 12, 1700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, true));
                waiting.add(spawn(paths[1], EnemyType.CIRCLE, true));
            }
        });
        // 16 enemies every 2400 ms, the first after 20000 ms. Original: di.
        addWave(new Wave(20000, 16, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, true));
            }
        });
        // 10 enemies every 2000 ms, the first after 20000 ms. Original: dj.
        addWave(new Wave(20000, 10, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                    Enemy enemy = spawn(paths[1], EnemyType.BALL_THREE, false);
                    enemy.setVisible(false);
                    waiting.add(enemy);
                    return;
                }
                Enemy enemy2 = spawn(paths[0], EnemyType.BALL_THREE, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 10 enemies every 2400 ms, the first after 20000 ms. Original: dk.
        addWave(new Wave(20000, 10, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                Enemy enemy = spawn(paths[0], EnemyType.SHREDDER, false);
                enemy.setVisible(false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[1], EnemyType.SHREDDER, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
            }
        });
        // 20 enemies every 2200 ms, the first after 20000 ms. Original: dl.
        addWave(new Wave(20000, 20, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.WORM, true));
                waiting.add(spawn(paths[1], EnemyType.SPARK, false));
            }
        });
        // 20 enemies every 1900 ms, the first after 20000 ms. Original: dm.
        addWave(new Wave(20000, 20, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, true));
                waiting.add(spawn(paths[1], EnemyType.VIRUS, true));
            }
        });
        // 14 enemies every 2100 ms, the first after 20000 ms. Original: cn.
        addWave(new Wave(20000, 14, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SUN_FIVE, false));
                waiting.add(spawn(paths[1], EnemyType.SUN_FIVE, false));
            }
        });
        // 14 enemies every 2800 ms, the first after 20000 ms. Original: co.
        addWave(new Wave(20000, 14, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SUN_FOUR, true));
                waiting.add(spawn(paths[1], EnemyType.SUN_FOUR, true));
            }
        });
        hud.start(200, waves());
    }
}
