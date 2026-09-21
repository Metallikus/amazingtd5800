package com.amazingtd5800;

import java.util.List;

/** Level 7 (background 3), starts with 250 money. Original: fn. */
final class LevelSeven extends Level {

    LevelSeven(Hud hud, int number) {
        super(hud, number, 3);
        // Path from entry point 0, 10 waypoints. Original: dd.
        Path path0 = new Path(10);
        addPath(path0);
        path0.add(new Vec2(snap(60), snap(0)));
        path0.add(new Vec2(snap(60), snap(200)));
        path0.add(new Vec2(snap(180), snap(200)));
        path0.add(new Vec2(snap(180), snap(300)));
        path0.add(new Vec2(snap(240), snap(300)));
        path0.add(new Vec2(snap(240), snap(380)));
        path0.add(new Vec2(snap(300), snap(380)));
        path0.add(new Vec2(snap(300), snap(420)));
        path0.add(new Vec2(snap(120), snap(420)));
        path0.add(new Vec2(snap(120), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 1, 10 waypoints. Original: dd.
        Path path1 = new Path(10);
        addPath(path1);
        path1.add(new Vec2(snap(76), snap(0)));
        path1.add(new Vec2(snap(76), snap(184)));
        path1.add(new Vec2(snap(196), snap(184)));
        path1.add(new Vec2(snap(196), snap(316)));
        path1.add(new Vec2(snap(260), snap(316)));
        path1.add(new Vec2(snap(260), snap(360)));
        path1.add(new Vec2(snap(310), snap(360)));
        path1.add(new Vec2(snap(310), snap(436)));
        path1.add(new Vec2(snap(140), snap(436)));
        path1.add(new Vec2(snap(140), snap(GameScreen.HEIGHT - 58)));
        // 8 enemies every 6000 ms, the first after 30000 ms. Original: pp.
        addWave(new Wave(30000, 8, 6000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
                waiting.add(spawn(paths[1], EnemyType.BALL, false));
            }
        });
        // 12 enemies every 3000 ms, the first after 20000 ms. Original: pr.
        addWave(new Wave(20000, 12, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.SUN_THREE, false));
            }
        });
        // 25 enemies every 2000 ms, the first after 20000 ms. Original: py.
        addWave(new Wave(20000, 25, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 14 enemies every 1600 ms, the first after 20000 ms. Original: px.
        addWave(new Wave(20000, 14, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.BLOB, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BLOB, false));
            }
        });
        // 16 enemies every 3400 ms, the first after 20000 ms. Original: qa.
        addWave(new Wave(20000, 16, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining < 8) {
                    waiting.add(spawn(paths[0], EnemyType.GHOST, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.GHOST, false));
            }
        });
        // 25 enemies every 2000 ms, the first after 20000 ms. Original: pz.
        addWave(new Wave(20000, 25, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER, true));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, true));
            }
        });
        // 10 enemies every 4000 ms, the first after 20000 ms. Original: pv.
        addWave(new Wave(20000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BALL, false));
                waiting.add(spawn(paths[1], EnemyType.BALL, false));
            }
        });
        // 22 enemies every 2000 ms, the first after 20000 ms. Original: pu.
        addWave(new Wave(20000, 22, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST, false));
                if (remaining % 2 == 1) {
                    interval = 2000;
                    return;
                }
                interval = 900;
            }
        });
        // 20 enemies every 900 ms, the first after 20000 ms. Original: pw.
        addWave(new Wave(20000, 20, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 12 enemies every 3000 ms, the first after 20000 ms. Original: ne.
        addWave(new Wave(20000, 12, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[1], EnemyType.CIRCLE, false));
            }
        });
        // 24 enemies every 1400 ms, the first after 20000 ms. Original: nd.
        addWave(new Wave(20000, 24, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining <= 4 || remaining <= 20) {
                    waiting.add(spawn(paths[0], EnemyType.WORM, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 18 enemies every 2000 ms, the first after 20000 ms. Original: nc.
        addWave(new Wave(20000, 18, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.LINE, false));
                waiting.add(spawn(paths[1], EnemyType.LINE_TWO, false));
            }
        });
        // 30 enemies every 700 ms, the first after 20000 ms. Original: nb.
        addWave(new Wave(20000, 30, 700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 20 enemies every 1000 ms, the first after 20000 ms. Original: na.
        addWave(new Wave(20000, 20, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.WORM, false));
            }
        });
        // 40 enemies every 900 ms, the first after 20000 ms. Original: my.
        addWave(new Wave(20000, 40, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
                waiting.add(spawn(paths[1], EnemyType.SHREDDER, false));
            }
        });
        // 30 enemies every 1400 ms, the first after 20000 ms. Original: nl.
        addWave(new Wave(20000, 30, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
                waiting.add(spawn(paths[1], EnemyType.TRIANGLE, false));
            }
        });
        // 10 enemies every 1800 ms, the first after 20000 ms. Original: nj.
        addWave(new Wave(20000, 10, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
                    return;
                }
                waiting.add(spawn(paths[1], EnemyType.CLOCK, false));
            }
        });
        // 1 enemies every 0 ms, the first after 20000 ms. Original: ng.
        addWave(new Wave(20000, 1, 0) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_BIG, false));
                waiting.add(spawn(paths[1], EnemyType.VIRUS_BIG, false));
            }
        });
        hud.start(250, waves());
    }
}
