package com.amazingtd5800;

import java.util.List;

/** Level 6 (background 4), starts with 100 money. Original: fp. */
final class LevelSix extends Level {

    LevelSix(Hud hud, int number) {
        super(hud, number, 4);
        // Path from entry point 0, 4 waypoints. Original: dd.
        Path path0 = new Path(4);
        addPath(path0);
        path0.add(new Vec2(snap(160), snap(GameScreen.HEIGHT - 58)));
        path0.add(new Vec2(snap(160), snap(350)));
        path0.add(new Vec2(snap(210), snap(300)));
        path0.add(new Vec2(snap(210), snap(0)));
        // Path from entry point 1, 4 waypoints. Original: dd.
        Path path1 = new Path(4);
        addPath(path1);
        path1.add(new Vec2(snap(210), snap(GameScreen.HEIGHT - 58)));
        path1.add(new Vec2(snap(210), snap(350)));
        path1.add(new Vec2(snap(160), snap(300)));
        path1.add(new Vec2(snap(160), snap(0)));
        // Path from entry point 2, 2 waypoints. Original: dd.
        Path path2 = new Path(2);
        addPath(path2);
        path2.add(new Vec2(snap(0), snap(128)));
        path2.add(new Vec2(snap(GameScreen.WIDTH), snap(128)));
        // Path from entry point 3, 2 waypoints. Original: dd.
        Path path3 = new Path(2);
        addPath(path3);
        path3.add(new Vec2(snap(GameScreen.WIDTH), snap(174)));
        path3.add(new Vec2(snap(0), snap(174)));
        // 10 enemies every 3500 ms, the first after 30000 ms. Original: pf.
        addWave(new Wave(30000, 10, 3500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, 10, 0.024f, 2, false));
            }
        });
        // 12 enemies every 3400 ms, the first after 20000 ms. Original: pd.
        addWave(new Wave(20000, 12, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, 16, 0.018f, 4, false));
            }
        });
        // 12 enemies every 3400 ms, the first after 20000 ms. Original: pe.
        addWave(new Wave(20000, 12, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.GHOST, 16, 0.018f, 4, false));
            }
        });
        // 8 enemies every 3200 ms, the first after 20000 ms. Original: tb.
        addWave(new Wave(20000, 8, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, 14, 0.024f, 2, false));
            }
        });
        // 8 enemies every 3200 ms, the first after 20000 ms. Original: tc.
        addWave(new Wave(20000, 8, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2);
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, 14, 0.024f, 2, false));
            }
        });
        // 8 enemies every 3200 ms, the first after 20000 ms. Original: td.
        addWave(new Wave(20000, 8, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(3);
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, 14, 0.024f, 2, false));
            }
        });
        // 6 enemies every 4000 ms, the first after 20000 ms. Original: te.
        addWave(new Wave(20000, 6, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, 16, 0.03f, 5, false));
            }
        });
        // 16 enemies every 2900 ms, the first after 20000 ms. Original: tf.
        addWave(new Wave(20000, 16, 2900) {
            /** Waypoint index the wave is walking now. Original: tf.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[index], EnemyType.LINE, 40, 0.024f, 3, false));
                index = (index + 1) % 4;
            }
        });
        // 6 enemies every 2800 ms, the first after 20000 ms. Original: tg.
        addWave(new Wave(20000, 6, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, 14, 0.024f, 2, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, 14, 0.024f, 2, false));
                waiting.add(spawn(paths[2], EnemyType.SHREDDER, 14, 0.024f, 2, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER, 14, 0.024f, 2, false));
            }
        });
        // 8 enemies every 3000 ms, the first after 20000 ms. Original: sn.
        addWave(new Wave(20000, 8, 3000) {
            /** Waypoint index the wave is walking now. Original: sn.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[index], EnemyType.SUN_THREE, false));
                index = (index + 1) % 4;
            }
        });
        // 12 enemies every 2500 ms, the first after 20000 ms. Original: so.
        addWave(new Wave(20000, 12, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.BLOB, 18, 0.03f, 3, false));
                waiting.add(spawn(paths[1], EnemyType.BLOB, 18, 0.03f, 3, false));
                waiting.add(spawn(paths[2], EnemyType.BLOB, 18, 0.03f, 3, false));
                waiting.add(spawn(paths[3], EnemyType.BLOB, 18, 0.03f, 3, false));
            }
        });
        // 16 enemies every 3200 ms, the first after 20000 ms. Original: sp.
        addWave(new Wave(20000, 16, 3200) {
            /** Waypoint index the wave is walking now. Original: sp.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[index], EnemyType.SUN_THREE, false));
                if (remaining % 2 == 0) {
                    interval = 1000;
                    return;
                }
                interval = 3200;
                index = (index + 1) % 4;
            }
        });
        // 18 enemies every 2000 ms, the first after 20000 ms. Original: sq.
        addWave(new Wave(20000, 18, 2000) {
            /** Waypoint index the wave is walking now. Original: sq.d. */
            int index = 2;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[index], EnemyType.SHREDDER, 20, 0.034f, 2, false));
                interval -= 10;
                if (remaining % 2 == 0) {
                    index = 2;
                    return;
                }
                index = 3;
            }
        });
        // 14 enemies every 3000 ms, the first after 20000 ms. Original: ss.
        addWave(new Wave(20000, 14, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.GHOST, 30, 0.018f, 3, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST, 30, 0.018f, 3, false));
            }
        });
        // 8 enemies every 2700 ms, the first after 20000 ms. Original: sd.
        addWave(new Wave(20000, 8, 2700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 16 enemies every 3100 ms, the first after 20000 ms. Original: se.
        addWave(new Wave(20000, 16, 3100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
                    waiting.add(spawn(paths[1], EnemyType.SUN_THREE, false));
                }
                waiting.add(spawn(paths[2], EnemyType.GHOST, 30, 0.018f, 2, false));
                waiting.add(spawn(paths[3], EnemyType.GHOST, 30, 0.018f, 2, false));
            }
        });
        // 12 enemies every 4000 ms, the first after 20000 ms. Original: sg.
        addWave(new Wave(20000, 12, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
                if (remaining % 3 == 0) {
                    waiting.add(spawn(paths[2], EnemyType.TRIANGLE, false));
                    waiting.add(spawn(paths[3], EnemyType.TRIANGLE, false));
                }
            }
        });
        // 24 enemies every 2500 ms, the first after 20000 ms. Original: sh.
        addWave(new Wave(20000, 24, 2500) {
            /** Waypoint index the wave is walking now. Original: sh.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[index], EnemyType.BALL, false));
                if (remaining % 4 == 0) {
                    interval = 2500;
                    index = (index + 1) % 4;
                    return;
                }
                interval = 900;
            }
        });
        // 18 enemies every 2600 ms, the first after 20000 ms. Original: si.
        addWave(new Wave(20000, 18, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[1], EnemyType.CIRCLE, false));
                if (remaining % 3 == 0) {
                    waiting.add(spawn(paths[2], EnemyType.BALL, true));
                    waiting.add(spawn(paths[3], EnemyType.BALL, true));
                }
            }
        });
        // 14 enemies every 2400 ms, the first after 20000 ms. Original: rk.
        addWave(new Wave(20000, 14, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 2 == 0) {
                    waiting.add(spawn(paths[0], EnemyType.ELLIPSE_TWO, false));
                    waiting.add(spawn(paths[1], EnemyType.ELLIPSE_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.LINE_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.LINE_TWO, false));
            }
        });
        // 1 enemies every 2800 ms, the first after 20000 ms. Original: rn.
        addWave(new Wave(20000, 1, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1, 3);
                waiting.add(spawn(paths[1], EnemyType.CLOCK, false));
                waiting.add(spawn(paths[3], EnemyType.CLOCK, false));
            }
        });
        // 16 enemies every 2000 ms, the first after 20000 ms. Original: rw.
        addWave(new Wave(20000, 16, 2000) {
            /** Waypoint index the wave is walking now. Original: rw.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[index], EnemyType.SUN_THREE, false));
                index = (index + 1) % 4;
                waiting.add(spawn(paths[index], EnemyType.TRIANGLE, false));
            }
        });
        // 20 enemies every 900 ms, the first after 20000 ms. Original: ry.
        addWave(new Wave(20000, 20, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS, false));
                if (remaining % 4 == 0) {
                    interval = 4000;
                    return;
                }
                interval = 900;
            }
        });
        // 12 enemies every 2400 ms, the first after 20000 ms. Original: rr.
        addWave(new Wave(20000, 12, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.GHOST, 40, 0.018f, 4, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST, 40, 0.018f, 4, false));
                waiting.add(spawn(paths[2], EnemyType.GHOST, 40, 0.018f, 4, false));
                waiting.add(spawn(paths[3], EnemyType.GHOST, 40, 0.018f, 4, false));
            }
        });
        // 12 enemies every 2000 ms, the first after 20000 ms. Original: rt.
        addWave(new Wave(20000, 12, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, false));
                waiting.add(spawn(paths[1], EnemyType.ELLIPSE, false));
                waiting.add(spawn(paths[2], EnemyType.WORM, false));
                waiting.add(spawn(paths[3], EnemyType.WORM, false));
            }
        });
        // 8 enemies every 3000 ms, the first after 20000 ms. Original: re.
        addWave(new Wave(20000, 8, 3000) {
            /** Waypoint index the wave is walking now. Original: re.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[index], EnemyType.CIRCLE, false));
                if (remaining % 4 == 3) {
                    interval = 3000;
                    index = (index + 1) % 4;
                    return;
                }
                interval = 900;
            }
        });
        // 18 enemies every 1000 ms, the first after 20000 ms. Original: rg.
        addWave(new Wave(20000, 18, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 6 == 0) {
                    waiting.add(spawn(paths[2], EnemyType.BALL_TWO, false));
                    waiting.add(spawn(paths[3], EnemyType.BALL_TWO, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 10 enemies every 2500 ms, the first after 20000 ms. Original: rc.
        addWave(new Wave(20000, 10, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER_THREE, false));
                    waiting.add(spawn(paths[1], EnemyType.SHREDDER_THREE, false));
                    return;
                }
                waiting.add(spawn(paths[2], EnemyType.SHREDDER_TWO, false));
                waiting.add(spawn(paths[3], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 10 enemies every 1600 ms, the first after 20000 ms. Original: rd.
        addWave(new Wave(20000, 10, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS, false));
            }
        });
        // 10 enemies every 1600 ms, the first after 0 ms. Original: qp.
        addWave(new Wave(0, 10, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(2, 3);
                waiting.add(spawn(paths[2], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[3], EnemyType.VIRUS, false));
            }
        });
        // 20 enemies every 1600 ms, the first after 0 ms. Original: qv.
        addWave(new Wave(0, 20, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1, 2, 3);
                waiting.add(spawn(paths[0], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[2], EnemyType.VIRUS, false));
                waiting.add(spawn(paths[3], EnemyType.VIRUS, false));
            }
        });
        hud.start(100, waves());
    }
}
