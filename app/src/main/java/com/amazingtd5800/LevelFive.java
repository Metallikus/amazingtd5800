package com.amazingtd5800;

import java.util.List;

/** Level 5 (background 5), starts with 140 money. Original: fz. */
final class LevelFive extends Level {

    LevelFive(Hud hud, int number) {
        super(hud, number, 5);
        // Path from entry point 0, 68 waypoints. Original: dd.
        Path path0 = new Path(68);
        addPath(path0);
        path0.add(new Vec2(snap(180), snap(0)));
        // Arc of radius 60 centred on the field axis. Original: ca.a(dd, n2, n3, n4, n5, n6, n7).
        arc(path0, 540, 220);
        // Arc of radius 60 centred on the field axis. Original: ca.a(dd, n2, n3, n4, n5, n6, n7).
        arc(path0, -540, 390);
        path0.add(new Vec2(snap(180), snap(GameScreen.HEIGHT - 58)));
        // 12 enemies every 800 ms, the first after 30000 ms. Original: jy.
        addWave(new Wave(30000, 12, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 4000 : 800;
                waiting.add(spawn(paths[0], EnemyType.GHOST, false));
            }
        });
        // 12 enemies every 900 ms, the first after 20000 ms. Original: jw.
        addWave(new Wave(20000, 12, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 3500 : 900;
                waiting.add(spawn(paths[0], EnemyType.SUN, false));
            }
        });
        // 12 enemies every 900 ms, the first after 20000 ms. Original: ke.
        addWave(new Wave(20000, 12, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 3000 : 900;
                waiting.add(spawn(paths[0], EnemyType.BLOB, false));
            }
        });
        // 30 enemies every 1800 ms, the first after 20000 ms. Original: ka.
        addWave(new Wave(20000, 30, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, false));
            }
        });
        // 18 enemies every 900 ms, the first after 20000 ms. Original: ms.
        addWave(new Wave(20000, 18, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 2 == 1 ? 4000 : 900;
                waiting.add(spawn(paths[0], EnemyType.BALL, true));
            }
        });
        // 20 enemies every 900 ms, the first after 20000 ms. Original: mt.
        addWave(new Wave(20000, 20, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.LINE_TWO, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 36 enemies every 1000 ms, the first after 20000 ms. Original: mq.
        addWave(new Wave(20000, 36, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 6 == 1 ? 4000 : 800;
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, false));
            }
        });
        // 16 enemies every 900 ms, the first after 20000 ms. Original: mr.
        addWave(new Wave(20000, 16, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 4000 : 900;
                waiting.add(spawn(paths[0], EnemyType.GHOST, true));
            }
        });
        // 24 enemies every 1000 ms, the first after 20000 ms. Original: mp.
        addWave(new Wave(20000, 24, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 3 == 1 ? 4000 : 1000;
                waiting.add(spawn(paths[0], EnemyType.TRIANGLE, false));
            }
        });
        // 14 enemies every 1500 ms, the first after 20000 ms. Original: qr.
        addWave(new Wave(20000, 14, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
            }
        });
        // 10 enemies every 1800 ms, the first after 20000 ms. Original: qo.
        addWave(new Wave(20000, 10, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.WORM, true);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 4 enemies every 5000 ms, the first after 20000 ms. Original: qu.
        addWave(new Wave(20000, 4, 5000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, 500, 0.018f, 4, false));
            }
        });
        // 16 enemies every 1000 ms, the first after 20000 ms. Original: qt.
        addWave(new Wave(20000, 16, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 3 == 1 ? 3500 : 900;
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, false));
            }
        });
        // 30 enemies every 3000 ms, the first after 20000 ms. Original: qx.
        addWave(new Wave(20000, 30, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_FOUR, false));
            }
        });
        // 20 enemies every 2800 ms, the first after 20000 ms. Original: qw.
        addWave(new Wave(20000, 20, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SPARK, false));
            }
        });
        // 40 enemies every 2500 ms, the first after 20000 ms. Original: qz.
        addWave(new Wave(20000, 40, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.VIRUS, true);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 24 enemies every 4000 ms, the first after 20000 ms. Original: qy.
        addWave(new Wave(20000, 24, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_FIVE, false));
            }
        });
        // 2 enemies every 10000 ms, the first after 20000 ms. Original: ra.
        addWave(new Wave(20000, 2, 10000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_BIG, false));
            }
        });
        hud.start(140, waves());
    }
}
