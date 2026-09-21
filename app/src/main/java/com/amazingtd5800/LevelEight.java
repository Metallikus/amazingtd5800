package com.amazingtd5800;

import java.util.List;

/** Level 8 (background 1), starts with 140 money. Original: fu. */
final class LevelEight extends Level {

    LevelEight(Hud hud, int number) {
        super(hud, number, 1);
        // Path from entry point 0, 12 waypoints. Original: dd.
        Path path0 = new Path(12);
        addPath(path0);
        path0.add(new Vec2(snap(GameScreen.WIDTH), snap(54)));
        path0.add(new Vec2(snap(34), snap(54)));
        path0.add(new Vec2(snap(34), snap(115)));
        path0.add(new Vec2(snap(233), snap(115)));
        path0.add(new Vec2(snap(233), snap(216)));
        path0.add(new Vec2(snap(165), snap(216)));
        path0.add(new Vec2(snap(165), snap(450)));
        path0.add(new Vec2(snap(30), snap(450)));
        path0.add(new Vec2(snap(30), snap(305)));
        path0.add(new Vec2(snap(254), snap(305)));
        path0.add(new Vec2(snap(254), snap(532)));
        path0.add(new Vec2(snap(0), snap(532)));
        // 250 enemies every 5000 ms, the first after 30000 ms. Original: lh.
        addWave(new Wave(30000, 250, 5000) {
            /** Waypoint index the wave is walking now. Original: lh.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining % 50 == 0) {
                    index += 2;
                }
                if (remaining % 10 == 0) {
                    interval -= 150;
                    waiting.add(spawn(paths[0], EnemyType.SUN_THREE, 12 + (250 - remaining), 0.03f, 10 + index, false));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.SHREDDER, 8 + (250 - remaining), 0.03f, 3 + index, false));
            }
        });
        // 300 enemies every 5000 ms, the first after 20000 ms. Original: ti.
        addWave(new Wave(20000, 300, 5000) {
            /** Waypoint index the wave is walking now. Original: ti.d. */
            int index = 0;
            @Override
            void spawn(Path[] paths, List<Enemy> waiting) {
                pathIndexes(0);
                if (remaining % 50 == 0) {
                    index += 3;
                }
                if (remaining % 10 == 0) {
                    interval -= 150;
                    waiting.add(spawn(paths[0], EnemyType.LINE_THREE, 200 + (300 - remaining), 0.034f, 30 + index, true));
                    return;
                }
                waiting.add(spawn(paths[0], EnemyType.GHOST, 150 + (300 - remaining), 0.034f, 5 + index, false));
            }
        });
        hud.start(140, waves());
    }
}
