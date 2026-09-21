package com.amazingtd5800;

import java.util.List;

/** Level 3 (background 4), starts with 200 money. Original: fw. */
final class LevelThree extends Level {

    LevelThree(Hud hud, int number) {
        super(hud, number, 4);
        // Path from entry point 0, 9 waypoints. Original: dd.
        Path path0 = new Path(9);
        addPath(path0);
        path0.add(new Vec2(snap(180), snap(0)));
        path0.add(new Vec2(snap(180), snap(300)));
        path0.add(new Vec2(snap(40), snap(224)));
        path0.add(new Vec2(snap(320), snap(224)));
        path0.add(new Vec2(snap(40), snap(400)));
        path0.add(new Vec2(snap(180), snap(130)));
        path0.add(new Vec2(snap(320), snap(400)));
        path0.add(new Vec2(snap(180), snap(300)));
        path0.add(new Vec2(snap(180), snap(GameScreen.HEIGHT - 58)));
        // 14 enemies every 2000 ms, the first after 30000 ms. Original: iz.
        addWave(new Wave(30000, 14, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE, false));
            }
        });
        // 16 enemies every 4000 ms, the first after 20000 ms. Original: jc.
        addWave(new Wave(20000, 16, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                if (remaining % 4 == 1) {
                    interval = 4000;
                    return;
                }
                interval = 800;
            }
        });
        // 20 enemies every 2400 ms, the first after 20000 ms. Original: ix.
        addWave(new Wave(20000, 20, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
                if (remaining % 4 == 1) {
                    interval = 3500;
                    return;
                }
                interval = 900;
            }
        });
        // 14 enemies every 2200 ms, the first after 20000 ms. Original: iy.
        addWave(new Wave(20000, 14, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
            }
        });
        // 20 enemies every 1800 ms, the first after 20000 ms. Original: iv.
        addWave(new Wave(20000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 16 enemies every 2400 ms, the first after 20000 ms. Original: iw.
        addWave(new Wave(20000, 16, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST, 70, 0.018f, 2, false));
            }
        });
        // 14 enemies every 2500 ms, the first after 20000 ms. Original: hx.
        addWave(new Wave(20000, 14, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL, 60, 0.03f, 1, false));
            }
        });
        // 30 enemies every 2000 ms, the first after 20000 ms. Original: hw.
        addWave(new Wave(20000, 30, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE_TWO, 80, 0.024f, 2, false));
            }
        });
        // 30 enemies every 2000 ms, the first after 20000 ms. Original: hz.
        addWave(new Wave(20000, 30, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining > 12) {
                    waiting.add(spawn(paths[0], EnemyType.BLOB, 70, 0.034f, 1, false));
                    return;
                }
                interval = 1700;
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, 110, 0.034f, 2, false));
            }
        });
        // 20 enemies every 1800 ms, the first after 20000 ms. Original: jb.
        addWave(new Wave(20000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.SHREDDER, true));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.BALL, true));
            }
        });
        // 16 enemies every 1300 ms, the first after 20000 ms. Original: ja.
        addWave(new Wave(20000, 16, 1300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, 120, 0.024f, 2, false));
            }
        });
        // 12 enemies every 4000 ms, the first after 20000 ms. Original: jg.
        addWave(new Wave(20000, 12, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: jf.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, false));
            }
        });
        // 20 enemies every 1800 ms, the first after 20000 ms. Original: je.
        addWave(new Wave(20000, 20, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.GHOST_TWO, 150, 0.018f, 2, false));
            }
        });
        // 20 enemies every 2800 ms, the first after 20000 ms. Original: jd.
        addWave(new Wave(20000, 20, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, 250, 0.024f, 3, false));
            }
        });
        // 25 enemies every 4000 ms, the first after 20000 ms. Original: jl.
        addWave(new Wave(20000, 25, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE_TWO, 100, 0.03f, 3, true));
                if (remaining % 5 == 4) {
                    interval = 4000;
                    return;
                }
                interval = 800;
            }
        });
        // 20 enemies every 2800 ms, the first after 20000 ms. Original: jk.
        addWave(new Wave(20000, 20, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE_THREE, 250, 0.03f, 2, false));
            }
        });
        // 30 enemies every 1400 ms, the first after 20000 ms. Original: ji.
        addWave(new Wave(20000, 30, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BLOB, 90, 0.034f, 2, true));
            }
        });
        // 3 enemies every 10000 ms, the first after 20000 ms. Original: jh.
        addWave(new Wave(20000, 3, 10000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_BIG, false));
            }
        });
        hud.start(200, waves());
    }
}
