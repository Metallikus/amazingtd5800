package com.amazingtd5800;

import java.util.List;

/** Level 10 (background 3), starts with 250 money. Original: qn. */
final class LevelTen extends Level {

    LevelTen(Hud hud, int number) {
        super(hud, number, 3);
        // Path from entry point 0, 92 waypoints. Original: dd.
        Path path0 = new Path(92);
        addPath(path0);
        path0.add(new Vec2(snap(0), snap(100)));
        // 91 spiral points, radius 180 minus 2 per step, angle step 0.21331801968819583 radians. Original: qn.
        double angle = 0.0;
        for (int i = 0, radius = 180; i < 91; i++) {
            path0.add(new Vec2(snap(170 + (int) (Math.sin(angle) * radius + 0.5)),
                    snap(280 - (int) (Math.cos(angle) * radius + 0.5))));
            angle += 0.21331801968819583;
            radius -= 2;
        }
        // 20 enemies every 900 ms, the first after 30000 ms. Original: bk.
        addWave(new Wave(30000, 20, 900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 4000 : 900;
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, true));
            }
        });
        // 18 enemies every 800 ms, the first after 20000 ms. Original: bl.
        addWave(new Wave(20000, 18, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 4000 : 800;
                Enemy enemy = spawn(paths[0], EnemyType.BLOB, true);
                waiting.add(enemy);
            }
        });
        // 12 enemies every 800 ms, the first after 20000 ms. Original: bg.
        addWave(new Wave(20000, 12, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 4300 : 800;
                Enemy enemy = spawn(paths[0], EnemyType.BLOB, true);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 20 enemies every 2400 ms, the first after 20000 ms. Original: bi.
        addWave(new Wave(20000, 20, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.WORM, true));
            }
        });
        // 20 enemies every 2100 ms, the first after 20000 ms. Original: bc.
        addWave(new Wave(20000, 20, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval -= 10;
                waiting.add(spawn(paths[0], EnemyType.SUN_THREE, true));
            }
        });
        // 30 enemies every 2100 ms, the first after 20000 ms. Original: be.
        addWave(new Wave(20000, 30, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 2 == 1 ? 4000 : 900;
                Enemy enemy = spawn(paths[0], EnemyType.BALL, true);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 30 enemies every 2100 ms, the first after 20000 ms. Original: bq.
        addWave(new Wave(20000, 30, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 2 == 1 ? 4000 : 900;
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, true));
            }
        });
        // 16 enemies every 2200 ms, the first after 20000 ms. Original: bs.
        addWave(new Wave(20000, 16, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, true));
            }
        });
        // 14 enemies every 1600 ms, the first after 20000 ms. Original: bn.
        addWave(new Wave(20000, 14, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, true));
            }
        });
        // 40 enemies every 3200 ms, the first after 20000 ms. Original: z.
        addWave(new Wave(20000, 40, 3200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining % 2 == 1) {
                    waiting.add(spawn(paths[0], EnemyType.WORM, true));
                    interval = 3200;
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.CIRCLE_TWO, true));
                interval = 900;
            }
        });
        // 18 enemies every 1900 ms, the first after 20000 ms. Original: y.
        addWave(new Wave(20000, 18, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_THREE, true));
            }
        });
        // 18 enemies every 1800 ms, the first after 20000 ms. Original: dz.
        addWave(new Wave(20000, 18, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE, true));
            }
        });
        // 30 enemies every 2100 ms, the first after 20000 ms. Original: dy.
        addWave(new Wave(20000, 30, 2100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 4 == 1 ? 4000 : 900;
                Enemy enemy = spawn(paths[0], EnemyType.BALL, true);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 20 enemies every 1600 ms, the first after 20000 ms. Original: dx.
        addWave(new Wave(20000, 20, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval -= 20;
                Enemy enemy = spawn(paths[0], EnemyType.BLOB, true);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 40 enemies every 1900 ms, the first after 20000 ms. Original: dw.
        addWave(new Wave(20000, 40, 1900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_FOUR, true));
            }
        });
        // 22 enemies every 4000 ms, the first after 20000 ms. Original: ed.
        addWave(new Wave(20000, 22, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                interval = remaining % 2 == 1 ? 4000 : 900;
                Enemy enemy = spawn(paths[0], EnemyType.SHREDDER_THREE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 18 enemies every 1800 ms, the first after 20000 ms. Original: ec.
        addWave(new Wave(20000, 18, 1800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SPARK, true));
            }
        });
        // 20 enemies every 1600 ms, the first after 20000 ms. Original: eb.
        addWave(new Wave(20000, 20, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_THREE, true));
            }
        });
        // 30 enemies every 1400 ms, the first after 20000 ms. Original: ea.
        addWave(new Wave(20000, 30, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining > 20) {
                    Enemy enemy = spawn(paths[0], EnemyType.VIRUS, true);
                    enemy.setVisible(false);
                    waiting.add(enemy);
                    return;
                }
                if (remaining > 6) {
                    interval = 1700;
                    waiting.add(spawn(paths[0], EnemyType.CIRCLE, true));
                    return;
                }
                interval = 2200;
                waiting.add(spawn(paths[0], EnemyType.SUN_FIVE, true));
            }
        });
        // 3 enemies every 10000 ms, the first after 20000 ms. Original: ej.
        addWave(new Wave(20000, 3, 10000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.VIRUS_BIG, true));
            }
        });
        hud.start(250, waves());
    }
}
