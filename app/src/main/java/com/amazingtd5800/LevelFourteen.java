package com.amazingtd5800;

import java.util.List;

/** Level 14 (background 3), starts with 300 money. Original: qi. */
final class LevelFourteen extends Level {

    LevelFourteen(Hud hud, int number) {
        super(hud, number, 3);
        // Path from entry point 0, 6 waypoints. Original: dd.
        Path path0 = new Path(6);
        addPath(path0);
        path0.add(new Vec2(snap(0), snap(240)));
        path0.add(new Vec2(snap(130), snap(240)));
        path0.add(new Vec2(snap(130), snap(60)));
        path0.add(new Vec2(snap(230), snap(60)));
        path0.add(new Vec2(snap(230), snap(240)));
        path0.add(new Vec2(snap(GameScreen.WIDTH), snap(240)));
        // Path from entry point 1, 6 waypoints. Original: dd.
        Path path1 = new Path(6);
        addPath(path1);
        path1.add(new Vec2(snap(0), snap(360)));
        path1.add(new Vec2(snap(130), snap(360)));
        path1.add(new Vec2(snap(130), snap(540)));
        path1.add(new Vec2(snap(230), snap(540)));
        path1.add(new Vec2(snap(230), snap(360)));
        path1.add(new Vec2(snap(GameScreen.WIDTH), snap(360)));
        // Path from entry point 2, 6 waypoints. Original: dd.
        Path path2 = new Path(6);
        addPath(path2);
        path2.add(new Vec2(snap(0), snap(280)));
        path2.add(new Vec2(snap(170), snap(280)));
        path2.add(new Vec2(snap(170), snap(100)));
        path2.add(new Vec2(snap(190), snap(100)));
        path2.add(new Vec2(snap(190), snap(280)));
        path2.add(new Vec2(snap(GameScreen.WIDTH), snap(280)));
        // Path from entry point 3, 6 waypoints. Original: dd.
        Path path3 = new Path(6);
        addPath(path3);
        path3.add(new Vec2(snap(0), snap(320)));
        path3.add(new Vec2(snap(170), snap(320)));
        path3.add(new Vec2(snap(170), snap(500)));
        path3.add(new Vec2(snap(190), snap(500)));
        path3.add(new Vec2(snap(190), snap(320)));
        path3.add(new Vec2(snap(GameScreen.WIDTH), snap(320)));
        // 20 enemies every 1800 ms, the first after 30000 ms. Original: jt.
        addWave(new Wave(30000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[2], EnemyType.LINE, false));
                    return;
                }
                waiting.add(spawn(paths[3], EnemyType.LINE, false));
            }
        });
        // 16 enemies every 1500 ms, the first after 20000 ms. Original: jr.
        addWave(new Wave(20000, 16, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[2], EnemyType.WORM, false));
                    return;
                }
                waiting.add(spawn(paths[3], EnemyType.WORM, false));
            }
        });
        // 20 enemies every 2700 ms, the first after 20000 ms. Original: kv.
        addWave(new Wave(20000, 20, 2700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.BALL, false));
            }
        });
        // 12 enemies every 3200 ms, the first after 20000 ms. Original: ku.
        addWave(new Wave(20000, 12, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[3], EnemyType.GHOST, false));
                waiting.add(spawn(paths[2], EnemyType.GHOST, false));
            }
        });
        // 20 enemies every 2400 ms, the first after 20000 ms. Original: ks.
        addWave(new Wave(20000, 20, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 20 enemies every 2700 ms, the first after 20000 ms. Original: kr.
        addWave(new Wave(20000, 20, 2700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.BALL, false));
                waiting.add(spawn(paths[3], EnemyType.BALL, false));
            }
        });
        // 18 enemies every 2800 ms, the first after 20000 ms. Original: kp.
        addWave(new Wave(20000, 18, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[3], EnemyType.CIRCLE, false));
            }
        });
        // 10 enemies every 3200 ms, the first after 20000 ms. Original: km.
        addWave(new Wave(20000, 10, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.LINE_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.LINE_TWO, false));
            }
        });
        // 15 enemies every 2000 ms, the first after 20000 ms. Original: kk.
        addWave(new Wave(20000, 15, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(3);
                waiting.add(spawn(paths[3], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 15 enemies every 2000 ms, the first after 20000 ms. Original: bz.
        addWave(new Wave(20000, 15, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 18 enemies every 2000 ms, the first after 20000 ms. Original: by.
        addWave(new Wave(20000, 18, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 2);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.CIRCLE_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.CIRCLE_TWO, false));
            }
        });
        // 12 enemies every 2200 ms, the first after 20000 ms. Original: bv.
        addWave(new Wave(20000, 12, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 12 enemies every 2200 ms, the first after 20000 ms. Original: bu.
        addWave(new Wave(20000, 12, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 18 enemies every 2000 ms, the first after 20000 ms. Original: bx.
        addWave(new Wave(20000, 18, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[1], EnemyType.CIRCLE_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[3], EnemyType.CIRCLE_TWO, false));
            }
        });
        // 30 enemies every 5000 ms, the first after 20000 ms. Original: bw.
        addWave(new Wave(20000, 30, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                interval = remaining % 4 == 0 ? 5000 : 800;
                if (remaining > 8) {
                    waiting.add(spawn(paths[3], EnemyType.SHREDDER, false));
                    waiting.add(spawn(paths[2], EnemyType.SHREDDER, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 12 enemies every 1200 ms, the first after 20000 ms. Original: sk.
        addWave(new Wave(20000, 12, 1200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
            }
        });
        // 30 enemies every 5000 ms, the first after 20000 ms. Original: sj.
        addWave(new Wave(20000, 30, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                interval = remaining % 4 == 0 ? 5000 : 800;
                if (remaining > 8) {
                    waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
                    waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 18 enemies every 3000 ms, the first after 20000 ms. Original: sm.
        addWave(new Wave(20000, 18, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                interval = remaining % 2 == 0 ? 900 : 3000;
                waiting.add(spawn(paths[1], EnemyType.VIRUS2, false));
                waiting.add(spawn(paths[0], EnemyType.VIRUS2, false));
            }
        });
        // 30 enemies every 1000 ms, the first after 20000 ms. Original: sl.
        addWave(new Wave(20000, 30, 1000) {
            /** Waypoint index the wave is walking now. Original: sl.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                index = (index + 1) % paths.length;
                waiting.add(spawn(paths[index], EnemyType.SUN_FOUR, false));
            }
        });
        // 18 enemies every 2100 ms, the first after 20000 ms. Original: sy.
        addWave(new Wave(20000, 18, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.LINE_TWO, false));
                if (remaining % 3 == 1) {
                    waiting.add(spawn(paths[1], EnemyType.CLOCK, false));
                    waiting.add(spawn(paths[2], EnemyType.CLOCK, false));
                }
                waiting.add(spawn(paths[3], EnemyType.LINE_TWO, false));
            }
        });
        // 22 enemies every 4000 ms, the first after 20000 ms. Original: sz.
        addWave(new Wave(20000, 22, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.BALL_TWO, true));
                waiting.add(spawn(paths[3], EnemyType.BALL_TWO, true));
            }
        });
        // 36 enemies every 1100 ms, the first after 20000 ms. Original: ta.
        addWave(new Wave(20000, 36, 1100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 3 == 1) {
                    waiting.add(spawn(paths[1], EnemyType.VIRUS, false));
                    waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.VIRUS, true));
                waiting.add(spawn(paths[3], EnemyType.VIRUS, true));
            }
        });
        // 22 enemies every 3500 ms, the first after 20000 ms. Original: su.
        addWave(new Wave(20000, 22, 3500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.BALL_TWO, true));
                waiting.add(spawn(paths[3], EnemyType.BALL_TWO, true));
            }
        });
        // 18 enemies every 2100 ms, the first after 20000 ms. Original: sv.
        addWave(new Wave(20000, 18, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
                if (remaining % 3 == 1) {
                    Enemy enemy = spawn(paths[1], EnemyType.SUN_THREE, false);
                    enemy.setVisible(false);
                    waiting.add(enemy);
                    Enemy enemy2 = spawn(paths[2], EnemyType.SUN_THREE, false);
                    enemy2.setVisible(false);
                    waiting.add(enemy2);
                }
                waiting.add(spawn(paths[3], EnemyType.GHOST, false));
            }
        });
        // 30 enemies every 1800 ms, the first after 20000 ms. Original: sw.
        addWave(new Wave(20000, 30, 1800) {
            /** Waypoint index the wave is walking now. Original: sw.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                index = (index + 1) % paths.length;
                waiting.add(spawn(paths[index], EnemyType.SHREDDER_THREE, false));
            }
        });
        // 30 enemies every 3900 ms, the first after 20000 ms. Original: sx.
        addWave(new Wave(20000, 30, 3900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                Enemy enemy = spawn(paths[2], EnemyType.TRIANGLE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[3], EnemyType.TRIANGLE, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
                if (remaining < 10) {
                    waiting.add(spawn(paths[0], EnemyType.BALL_TWO, true));
                    waiting.add(spawn(paths[1], EnemyType.BALL_TWO, true));
                    return;
                }
                if (remaining < 20) {
                    waiting.add(spawn(paths[0], EnemyType.TRIANGLE_TWO, false));
                    waiting.add(spawn(paths[1], EnemyType.TRIANGLE_TWO, false));
                }
            }
        });
        hud.start(300, waves());
    }
}
