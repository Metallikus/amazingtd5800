package com.amazingtd5800;

import java.util.List;

/** Level 16 (background 3), starts with 150 money. Original: qf. */
final class LevelSixteen extends Level {

    LevelSixteen(Hud hud, int number) {
        super(hud, number, 3);
        // Path from entry point 0, 14 waypoints. Original: dd.
        Path path0 = new Path(14);
        addPath(path0);
        path0.add(new Vec2(snap(152), snap(0)));
        path0.add(new Vec2(snap(152), snap(100)));
        path0.add(new Vec2(snap(112), snap(100)));
        path0.add(new Vec2(snap(112), snap(204)));
        path0.add(new Vec2(snap(152), snap(204)));
        path0.add(new Vec2(snap(152), snap(160)));
        path0.add(new Vec2(snap(64), snap(160)));
        path0.add(new Vec2(snap(64), snap(312)));
        path0.add(new Vec2(snap(152), snap(312)));
        path0.add(new Vec2(snap(152), snap(276)));
        path0.add(new Vec2(snap(112), snap(276)));
        path0.add(new Vec2(snap(112), snap(380)));
        path0.add(new Vec2(snap(152), snap(380)));
        path0.add(new Vec2(snap(152), snap(GameScreen.HEIGHT - 58)));
        // Path from entry point 1, 14 waypoints. Original: dd.
        Path path1 = new Path(14);
        addPath(path1);
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(0)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(100)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 112), snap(100)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 112), snap(204)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(204)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(160)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 64), snap(160)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 64), snap(312)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(312)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(276)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 112), snap(276)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 112), snap(380)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(380)));
        path1.add(new Vec2(snap(GameScreen.WIDTH - 152), snap(GameScreen.HEIGHT - 58)));
        // 8 enemies every 7000 ms, the first after 30000 ms. Original: ld.
        addWave(new Wave(30000, 8, 7000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.CIRCLE, false));
                waiting.add(spawn(paths[1], EnemyType.CIRCLE, false));
            }
        });
        // 20 enemies every 2500 ms, the first after 20000 ms. Original: le.
        addWave(new Wave(20000, 20, 2500) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 20 enemies every 2900 ms, the first after 20000 ms. Original: la.
        addWave(new Wave(20000, 20, 2900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.LINE, false));
            }
        });
        // 8 enemies every 11000 ms, the first after 20000 ms. Original: lc.
        addWave(new Wave(20000, 8, 11000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
            }
        });
        // 10 enemies every 2800 ms, the first after 20000 ms. Original: ky.
        addWave(new Wave(20000, 10, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN, true));
            }
        });
        // 20 enemies every 2900 ms, the first after 20000 ms. Original: kz.
        addWave(new Wave(20000, 20, 2900) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.LINE, false));
            }
        });
        // 10 enemies every 2800 ms, the first after 20000 ms. Original: kw.
        addWave(new Wave(20000, 10, 2800) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.SUN, true));
            }
        });
        // 8 enemies every 11000 ms, the first after 20000 ms. Original: kx.
        addWave(new Wave(20000, 8, 11000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.CLOCK, false));
            }
        });
        // 40 enemies every 5000 ms, the first after 20000 ms. Original: ny.
        addWave(new Wave(20000, 40, 5000) {
            /** Waypoint index the wave is walking now. Original: ny.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining % 4 == 3) {
                    interval = 5000;
                    index = (index + 1) % paths.length;
                }
                else {
                    interval = 900;
                }
                waiting.add(spawn(paths[index], EnemyType.LINE, true));
            }
        });
        // 8 enemies every 4100 ms, the first after 20000 ms. Original: da.
        addWave(new Wave(20000, 8, 4100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.GHOST_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.GHOST_THREE, false));
            }
        });
        // 24 enemies every 1000 ms, the first after 20000 ms. Original: cy.
        addWave(new Wave(20000, 24, 1000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.BALL_THREE, false));
                waiting.add(spawn(paths[1], EnemyType.BALL_THREE, false));
            }
        });
        // 8 enemies every 4100 ms, the first after 20000 ms. Original: dd.
        addWave(new Wave(20000, 8, 4100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                waiting.add(spawn(paths[1], EnemyType.SUN_FIVE, false));
            }
        });
        // 8 enemies every 4100 ms, the first after 20000 ms. Original: dc.
        addWave(new Wave(20000, 8, 4100) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                waiting.add(spawn(paths[0], EnemyType.SUN_FIVE, false));
            }
        });
        // 14 enemies every 4000 ms, the first after 20000 ms. Original: cs.
        addWave(new Wave(20000, 14, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.GHOST_TWO, false);
                if (remaining % 2 == 1) {
                    enemy.setVisible(false);
                }
                waiting.add(enemy);
            }
        });
        // 30 enemies every 1600 ms, the first after 20000 ms. Original: cq.
        addWave(new Wave(20000, 30, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.BALL_THREE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 12 enemies every 3000 ms, the first after 20000 ms. Original: cw.
        addWave(new Wave(20000, 12, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                Enemy enemy = spawn(paths[0], EnemyType.TRIANGLE_TWO, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 30 enemies every 1600 ms, the first after 20000 ms. Original: cu.
        addWave(new Wave(20000, 30, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                Enemy enemy = spawn(paths[1], EnemyType.BALL_THREE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 12 enemies every 3000 ms, the first after 20000 ms. Original: eq.
        addWave(new Wave(20000, 12, 3000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(1);
                Enemy enemy = spawn(paths[1], EnemyType.TRIANGLE_TWO, false);
                enemy.setVisible(false);
                waiting.add(enemy);
            }
        });
        // 10 enemies every 6000 ms, the first after 20000 ms. Original: et.
        addWave(new Wave(20000, 10, 6000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.CLOCK, false));
                waiting.add(spawn(paths[1], EnemyType.CLOCK, false));
            }
        });
        // 30 enemies every 1600 ms, the first after 20000 ms. Original: ey.
        addWave(new Wave(20000, 30, 1600) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                Enemy enemy = spawn(paths[0], EnemyType.BALL_THREE, false);
                enemy.setVisible(false);
                waiting.add(enemy);
                Enemy enemy2 = spawn(paths[1], EnemyType.BALL_THREE, false);
                enemy2.setVisible(false);
                waiting.add(enemy2);
            }
        });
        // 8 enemies every 4000 ms, the first after 20000 ms. Original: ew.
        addWave(new Wave(20000, 8, 4000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.GHOST_THREE, true));
                waiting.add(spawn(paths[1], EnemyType.GHOST_THREE, true));
            }
        });
        // 22 enemies every 2400 ms, the first after 20000 ms. Original: ev.
        addWave(new Wave(20000, 22, 2400) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                if (remaining >= 12) {
                    waiting.add(spawn(paths[1], EnemyType.VIRUS_TWO, false));
                }
                else if (remaining >= 2) {
                    waiting.add(spawn(paths[0], EnemyType.VIRUS_TWO, false));
                }
                else {
                    waiting.add(spawn(paths[0], EnemyType.VIRUS_TWO, false));
                    waiting.add(spawn(paths[1], EnemyType.VIRUS_TWO, false));
                }
                interval -= 10;
            }
        });
        // 10 enemies every 8000 ms, the first after 20000 ms. Original: eu.
        addWave(new Wave(20000, 10, 8000) {
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0, 1);
                waiting.add(spawn(paths[0], EnemyType.SPARK_TWO, false));
                waiting.add(spawn(paths[1], EnemyType.SPARK_TWO, false));
                interval -= 50;
            }
        });
        hud.start(150, waves());
    }
}
