package com.amazingtd5800;

import java.util.List;

/** Level 19 (background 5), starts with 300 money. Original: qb. */
final class LevelNineteen extends Level {

    LevelNineteen(Hud hud, int number) {
        super(hud, number, 5);
        // Path from entry point 0, 8 waypoints. Original: dd.
        Path path0 = new Path(8);
        addPath(path0);
        path0.add(new Vec2(snap(132), snap(GameScreen.HEIGHT - 58)));
        path0.add(new Vec2(snap(132), snap(GameScreen.HEIGHT - 58 - 400)));
        path0.add(new Vec2(snap(150), snap(GameScreen.HEIGHT - 58 - 400)));
        path0.add(new Vec2(snap(150), snap(GameScreen.HEIGHT - 58 - 80)));
        path0.add(new Vec2(snap(200), snap(GameScreen.HEIGHT - 58 - 80)));
        path0.add(new Vec2(snap(200), snap(GameScreen.HEIGHT - 58 - 300)));
        path0.add(new Vec2(snap(222), snap(GameScreen.HEIGHT - 58 - 300)));
        path0.add(new Vec2(snap(222), snap(GameScreen.HEIGHT - 58)));
        // 20 enemies every 3000 ms, the first after 30000 ms. Original: ci.
        addWave(new Wave(30000, 20, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.BALL, false);
                enemy.addEffect(new Heal());
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 10 enemies every 4000 ms, the first after 20000 ms. Original: ce.
        addWave(new Wave(20000, 10, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.VIRUS2, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 14 enemies every 3600 ms, the first after 20000 ms. Original: cf.
        addWave(new Wave(20000, 14, 3600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.GHOST_TWO, false);
                enemy.addEffect(new Heal());
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 20 enemies every 2200 ms, the first after 20000 ms. Original: cg.
        addWave(new Wave(20000, 20, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.SUN_THREE, false);
                enemy.addEffect(new Heal());
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 24 enemies every 1700 ms, the first after 20000 ms. Original: ch.
        addWave(new Wave(20000, 24, 1700) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.SHREDDER_TWO, false);
                enemy.addEffect(new Heal());
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 14 enemies every 2600 ms, the first after 20000 ms. Original: d.
        addWave(new Wave(20000, 14, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.CIRCLE_TWO, false);
                enemy.addEffect(new Heal());
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 10 enemies every 2400 ms, the first after 20000 ms. Original: c.
        addWave(new Wave(20000, 10, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.LINE_THREE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 20 enemies every 2000 ms, the first after 20000 ms. Original: b.
        addWave(new Wave(20000, 20, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.BALL_THREE, true);
                enemy.addEffect(new Heal());
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 26 enemies every 800 ms, the first after 20000 ms. Original: a.
        addWave(new Wave(20000, 26, 800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.SUN, false);
                enemy.addEffect(new Heal());
                waiting.add(enemy);
            }
        });
        // 16 enemies every 2600 ms, the first after 20000 ms. Original: gq.
        addWave(new Wave(20000, 16, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE_TWO, true));
            }
        });
        // 14 enemies every 2200 ms, the first after 20000 ms. Original: gt.
        addWave(new Wave(20000, 14, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SHREDDER_TWO, true));
            }
        });
        // 30 enemies every 1400 ms, the first after 20000 ms. Original: gs.
        addWave(new Wave(20000, 30, 1400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.ELLIPSE_THREE, false));
            }
        });
        // 16 enemies every 2000 ms, the first after 20000 ms. Original: gm.
        addWave(new Wave(20000, 16, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.WORM_TWO, false));
            }
        });
        // 26 enemies every 3400 ms, the first after 20000 ms. Original: gl.
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
        // 12 enemies every 2500 ms, the first after 20000 ms. Original: gp.
        addWave(new Wave(20000, 12, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SPARK, false));
            }
        });
        // 15 enemies every 2000 ms, the first after 20000 ms. Original: go.
        addWave(new Wave(20000, 15, 2000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, true));
            }
        });
        // 18 enemies every 2200 ms, the first after 20000 ms. Original: gj.
        addWave(new Wave(20000, 18, 2200) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE_TWO, false));
            }
        });
        // 10 enemies every 3000 ms, the first after 20000 ms. Original: gi.
        addWave(new Wave(20000, 10, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SPARK_TWO, false));
            }
        });
        // 20 enemies every 1500 ms, the first after 20000 ms. Original: gk.
        addWave(new Wave(20000, 20, 1500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, true));
            }
        });
        // 16 enemies every 2600 ms, the first after 20000 ms. Original: gf.
        addWave(new Wave(20000, 16, 2600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, 400, 0.024f, 1, false));
            }
        });
        // 10 enemies every 2300 ms, the first after 20000 ms. Original: ge.
        addWave(new Wave(20000, 10, 2300) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.WORM_TWO, true));
            }
        });
        // 8 enemies every 3400 ms, the first after 20000 ms. Original: gd.
        addWave(new Wave(20000, 8, 3400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK_TWO, false));
            }
        });
        // 3 enemies every 5000 ms, the first after 20000 ms. Original: gc.
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
