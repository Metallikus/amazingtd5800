package com.amazingtd5800;

import java.util.List;

/** Level 12 (background 5), starts with 400 money. Original: qk. */
final class LevelTwelve extends Level {

    LevelTwelve(Hud hud, int number) {
        super(hud, number, 5);
        // Path from entry point 0, 10 waypoints. Original: dd.
        Path path0 = new Path(10);
        addPath(path0);
        path0.add(new Vec2(snap(100), snap(0)));
        path0.add(new Vec2(snap(100), snap(60)));
        path0.add(new Vec2(snap(140), snap(90)));
        path0.add(new Vec2(snap(140), snap(170)));
        path0.add(new Vec2(snap(220), snap(230)));
        path0.add(new Vec2(snap(220), snap(340)));
        path0.add(new Vec2(snap(260), snap(370)));
        path0.add(new Vec2(snap(260), snap(470)));
        path0.add(new Vec2(snap(220), snap(500)));
        path0.add(new Vec2(snap(220), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 1, 8 waypoints. Original: dd.
        Path path1 = new Path(8);
        addPath(path1);
        path1.add(new Vec2(snap(140), snap(0)));
        path1.add(new Vec2(snap(140), snap(60)));
        path1.add(new Vec2(snap(180), snap(90)));
        path1.add(new Vec2(snap(180), snap(170)));
        path1.add(new Vec2(snap(100), snap(230)));
        path1.add(new Vec2(snap(100), snap(340)));
        path1.add(new Vec2(snap(180), snap(400)));
        path1.add(new Vec2(snap(180), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 2, 8 waypoints. Original: dd.
        Path path2 = new Path(8);
        addPath(path2);
        path2.add(new Vec2(snap(180), snap(0)));
        path2.add(new Vec2(snap(180), snap(60)));
        path2.add(new Vec2(snap(100), snap(90)));
        path2.add(new Vec2(snap(100), snap(170)));
        path2.add(new Vec2(snap(140), snap(230)));
        path2.add(new Vec2(snap(140), snap(340)));
        path2.add(new Vec2(snap(100), snap(370)));
        path2.add(new Vec2(snap(100), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 3, 10 waypoints. Original: dd.
        Path path3 = new Path(10);
        addPath(path3);
        path3.add(new Vec2(snap(220), snap(0)));
        path3.add(new Vec2(snap(220), snap(60)));
        path3.add(new Vec2(snap(260), snap(90)));
        path3.add(new Vec2(snap(260), snap(170)));
        path3.add(new Vec2(snap(180), snap(230)));
        path3.add(new Vec2(snap(180), snap(340)));
        path3.add(new Vec2(snap(220), snap(400)));
        path3.add(new Vec2(snap(220), snap(470)));
        path3.add(new Vec2(snap(260), snap(500)));
        path3.add(new Vec2(snap(260), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 4, 8 waypoints. Original: dd.
        Path path4 = new Path(8);
        addPath(path4);
        path4.add(new Vec2(snap(260), snap(0)));
        path4.add(new Vec2(snap(260), snap(60)));
        path4.add(new Vec2(snap(220), snap(90)));
        path4.add(new Vec2(snap(220), snap(170)));
        path4.add(new Vec2(snap(260), snap(200)));
        path4.add(new Vec2(snap(260), snap(340)));
        path4.add(new Vec2(snap(140), snap(400)));
        path4.add(new Vec2(snap(140), snap(GameScreen.HEIGHT - 58)));
        // 20 enemies every 3000 ms, the first after 30000 ms. Original: at.
        addWave(new Wave(30000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 3);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
            }
        });
        // 16 enemies every 4000 ms, the first after 20000 ms. Original: ar.
        addWave(new Wave(20000, 16, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 2800 ms, the first after 20000 ms. Original: ao.
        addWave(new Wave(20000, 20, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.GHOST, false));
            }
        });
        // 40 enemies every 2400 ms, the first after 20000 ms. Original: al.
        addWave(new Wave(20000, 40, 2400) {
            /** Waypoint index the wave is walking now. Original: al.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[index], EnemyType.ELLIPSE, false));
                if (remaining % 2 == 1) {
                    ++index;
                    index %= 5;
                    interval = 2400;
                    return;
                }
                interval = 900;
            }
        });
        // 10 enemies every 4000 ms, the first after 20000 ms. Original: ai.
        addWave(new Wave(20000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 2100 ms, the first after 20000 ms. Original: ah.
        addWave(new Wave(20000, 20, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3);
                waiting.add(spawn(paths[1], EnemyType.GHOST, false));
                waiting.add(spawn(paths[2], EnemyType.BALL, false));
                waiting.add(spawn(paths[3], EnemyType.GHOST, false));
            }
        });
        // 20 enemies every 3000 ms, the first after 20000 ms. Original: af.
        addWave(new Wave(20000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[1], EnemyType.BALL, false));
                    waiting.add(spawn(paths[2], EnemyType.BALL, false));
                    waiting.add(spawn(paths[3], EnemyType.BALL, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[2], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[3], EnemyType.BALL_THREE, false));
            }
        });
        // 5 enemies every 5500 ms, the first after 20000 ms. Original: ad.
        addWave(new Wave(20000, 5, 5500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 2, 3);
                waiting.add(spawn(paths[1], EnemyType.SPARK, false));
                waiting.add(spawn(paths[2], EnemyType.SPARK, false));
                waiting.add(spawn(paths[3], EnemyType.SPARK, false));
            }
        });
        // 20 enemies every 2400 ms, the first after 20000 ms. Original: ab.
        addWave(new Wave(20000, 20, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 4);
                Enemy enemy = spawn(paths[0], EnemyType.LINE_TWO, false);
                enemy.setVisible(false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[4], EnemyType.GHOST, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
            }
        });
        // 24 enemies every 1500 ms, the first after 20000 ms. Original: es.
        addWave(new Wave(20000, 24, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.pick(paths.length)], EnemyType.CIRCLE, false));
            }
        });
        // 14 enemies every 3000 ms, the first after 20000 ms. Original: er.
        addWave(new Wave(20000, 14, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2, 4);
                interval = remaining % 2 == 1 ? 3000 : 1000;
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 6 enemies every 5000 ms, the first after 20000 ms. Original: ep.
        addWave(new Wave(20000, 6, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[Randoms.pick(paths.length)], EnemyType.BALL_TWO, false));
            }
        });
        // 12 enemies every 4000 ms, the first after 20000 ms. Original: en.
        addWave(new Wave(20000, 12, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 3);
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, true));
                waiting.add(spawn(paths[3], EnemyType.TRIANGLE, true));
            }
        });
        // 10 enemies every 3800 ms, the first after 20000 ms. Original: cr.
        addWave(new Wave(20000, 10, 3800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_THREE, false));
            }
        });
        // 20 enemies every 2800 ms, the first after 20000 ms. Original: ct.
        addWave(new Wave(20000, 20, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 3, 4);
                Enemy enemy = spawn(paths[0], EnemyType.SUN_THREE, false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[1], EnemyType.SUN_THREE, false);
                waiting.add(enemy2);
                Enemy enemy3 = spawn(paths[3], EnemyType.SUN_THREE, false);
                waiting.add(enemy3);
                Enemy enemy4 = spawn(paths[4], EnemyType.SUN_THREE, false);
                waiting.add(enemy4);
            }
        });
        // 10 enemies every 4000 ms, the first after 20000 ms. Original: cv.
        addWave(new Wave(20000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_THREE, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER, false));
            }
        });
        // 20 enemies every 1000 ms, the first after 20000 ms. Original: cx.
        addWave(new Wave(20000, 20, 1000) {
            /** Waypoint index the wave is walking now. Original: cx.d. */
            int index = 2;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[index], EnemyType.SHREDDER_TWO, true));
                index = (index + 1) % 5;
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: cz.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                int n2 = remaining % 4;
                switch (n2) {
                    case 0: {
                        waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                        return;
                    }
                    case 1: {
                        waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                        waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
                        return;
                    }
                    case 2: {
                        waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                        waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
                        return;
                    }
                    case 3: {
                        waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                        waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
                        waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
                        waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
                        waiting.add(spawn(paths[4], EnemyType.SHREDDER, false));
                    }
                }
            }
        });
        // 5 enemies every 8000 ms, the first after 20000 ms. Original: db.
        addWave(new Wave(20000, 5, 8000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2, 4);
                waiting.add(spawn(paths[0], EnemyType.SPARK_TWO, false));
                waiting.add(spawn(paths[2], EnemyType.SPARK_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SPARK_TWO, false));
            }
        });
        // 20 enemies every 8000 ms, the first after 20000 ms. Original: ck.
        addWave(new Wave(20000, 20, 8000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                Enemy enemy = spawn(paths[0], EnemyType.SHREDDER_TWO, true);
                enemy.setMoney(1);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[1], EnemyType.BALL, false);
                enemy2.setMoney(1);
                enemy2.setVisible(false);
                waiting.add(enemy2);
                Enemy enemy3 = spawn(paths[2], EnemyType.SUN_FOUR, false);
                enemy3.setMoney(1);
                enemy3.setVisible(false);
                waiting.add(enemy3);
                Enemy enemy4 = spawn(paths[3], EnemyType.CIRCLE, false);
                enemy4.setMoney(1);
                enemy4.setVisible(false);
                waiting.add(enemy4);
                Enemy enemy5 = spawn(paths[4], EnemyType.SHREDDER_TWO, true);
                enemy5.setMoney(1);
                waiting.add(enemy5);
            }
        });
        // 14 enemies every 3000 ms, the first after 20000 ms. Original: cm.
        addWave(new Wave(20000, 14, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                if (remaining % 2 == 1) {
                    interval = 3000;
                    waiting.add(spawn(paths[2], EnemyType.SHREDDER_THREE, false));
                }
                else {
                    interval = 1000;
                }
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[4], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 15 enemies every 3000 ms, the first after 20000 ms. Original: cl.
        addWave(new Wave(20000, 15, 3000) {
            /** Waypoint index the wave is walking now. Original: cl.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3, 4);
                waiting.add(spawn(paths[index], EnemyType.SPARK, false));
                if (remaining % 2 == 1) {
                    ++index;
                    index %= 5;
                    interval = 3000;
                    return;
                }
                interval = 900;
            }
        });
        hud.start(400, waves());
    }
}
