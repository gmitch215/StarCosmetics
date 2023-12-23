package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.emote.Emote;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The original thread that the original code belongs to can be found here:
 * https://www.spigotmc.org/threads/armor-stand-animator-class.152863/
 * MIT License

 Copyright (c) 2016 Bram Stout
 Copyright (c) 2023 Gregory Mitchell

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 * @author Bram
 * @author gamer
 */
public final class StarAnimator {

    public static Set<StarAnimator> animators = new HashSet<>();

    public static void updateAll() {
        animators.forEach(StarAnimator::update);
    }

    private UUID owner;
    private ArmorStand armorStand;
    private int length;
    private Frame[] frames;
    private boolean stopped = false;
    private int currentFrame;
    private Location startLocation;

    private void readFrames(InputStream stream) {
        if (stream == null) throw new IllegalArgumentException("Null input stream for animation file");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(stream));
            String line;
            Frame currentFrame = null;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\s");

                if (line.startsWith("length")) {
                    length = (int) Float.parseFloat(split[1]);
                    frames = new Frame[length];
                }
                else if (line.startsWith("frame")) {
                    if (currentFrame != null) {
                        frames[currentFrame.frameID] = currentFrame;
                    }
                    int frameID = Integer.parseInt(split[1]);
                    currentFrame = new Frame();
                    currentFrame.frameID = frameID;
                }
                else if (line.contains("Armorstand_Position")) {
                    // Blender uses Z as up and Y as forward
                    currentFrame.x = Float.parseFloat(split[1]);
                    currentFrame.y = Float.parseFloat(split[3]);
                    currentFrame.z = -Float.parseFloat(split[2]);
                    currentFrame.r = Float.parseFloat(split[4]);
                }
                else if (line.contains("Armorstand_Middle")) {
                    float x = (float) Math.toRadians(Float.parseFloat(split[1]));
                    float y = (float) Math.toRadians(Float.parseFloat(split[2]));
                    float z = (float) Math.toRadians(Float.parseFloat(split[3]));
                    currentFrame.middle = new EulerAngle(x, y, z);
                }
                else if (line.contains("Armorstand_Right_Leg")) {
                    float x = (float) Math.toRadians(Float.parseFloat(split[1]));
                    float y = (float) Math.toRadians(Float.parseFloat(split[2]));
                    float z = (float) Math.toRadians(Float.parseFloat(split[3]));
                    currentFrame.rightLeg = new EulerAngle(x, y, z);
                }
                else if (line.contains("Armorstand_Left_Leg")) {
                    float x = (float) Math.toRadians(Float.parseFloat(split[1]));
                    float y = (float) Math.toRadians(Float.parseFloat(split[2]));
                    float z = (float) Math.toRadians(Float.parseFloat(split[3]));
                    currentFrame.leftLeg = new EulerAngle(x, y, z);
                }
                else if (line.contains("Armorstand_Left_Arm")) {
                    float x = (float) Math.toRadians(Float.parseFloat(split[1]));
                    float y = (float) Math.toRadians(Float.parseFloat(split[2]));
                    float z = (float) Math.toRadians(Float.parseFloat(split[3]));
                    currentFrame.leftArm = new EulerAngle(x, y, z);
                }
                else if (line.contains("Armorstand_Right_Arm")) {
                    float x = (float) Math.toRadians(Float.parseFloat(split[1]));
                    float y = (float) Math.toRadians(Float.parseFloat(split[2]));
                    float z = (float) Math.toRadians(Float.parseFloat(split[3]));
                    currentFrame.rightArm = new EulerAngle(x, y, z);
                }
                else if (line.contains("Armorstand_Head")) {
                    float x = (float) Math.toRadians(Float.parseFloat(split[1]));
                    float y = (float) Math.toRadians(Float.parseFloat(split[2]));
                    float z = (float) Math.toRadians(Float.parseFloat(split[3]));
                    currentFrame.head = new EulerAngle(x, y, z);
                }
            }
            if (currentFrame != null) {
                frames[currentFrame.frameID] = currentFrame;
            }
        } catch (Exception ex) {
            StarConfig.print(ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                    StarConfig.print(ex);
                }
            }
        }
    }

    public StarAnimator(Emote emote, ArmorStand armorStand, UUID owner) {
        this(emote.getFile(), armorStand, owner);
    }

    public StarAnimator(InputStream stream, ArmorStand armorStand, UUID owner) {
        this.armorStand = armorStand;
        this.owner = owner;

        startLocation = armorStand.getLocation();
        readFrames(stream);

        animators.add(this);
    }

    public void remove() {
        animators.remove(this);
    }

    public boolean isStopped() {
        return stopped;
    }

    public boolean isOwner(@NotNull OfflinePlayer p) {
        return p.getUniqueId().equals(owner);
    }

    public void stop() {
        stopped = true;
    }

    public void play() {
        play(null);
    }

    public void play(@Nullable Runnable after) {
        AtomicBoolean ranOnce = new AtomicBoolean(false);

        new BukkitRunnable() {
            public void run() {
                Runnable cancel = () -> {
                    currentFrame = 0;
                    stopped = true;
                    if (after != null) after.run();
                    cancel();
                    remove();
                };

                if (currentFrame >= length) {
                    if (ranOnce.get()) {
                        cancel.run();
                        return;
                    }
                    else {
                        ranOnce.set(true);
                        currentFrame = 0;
                    }
                    return;
                }

                if (stopped) {
                    cancel();
                    return;
                }

                try {
                    update();
                } catch (Exception ex) {
                    StarConfig.print(ex);
                    cancel.run();
                }
            }
        }.runTaskTimer(StarConfig.getPlugin(), 0, 1);
    }

    public void update() {
        if (stopped) return;

        Frame f = frames[currentFrame];
        if (f == null) f = interpolate(currentFrame);

        if (f != null) {
            Location newLoc = startLocation.clone().add(f.x, f.y, f.z);
            newLoc.setYaw(f.r + newLoc.getYaw());
            armorStand.teleport(newLoc);
            if (f.head != null)
                armorStand.setHeadPose(f.head);

            if (f.middle != null)
                armorStand.setBodyPose(f.middle);

            if (f.leftLeg != null)
                armorStand.setLeftLegPose(f.leftLeg);

            if (f.leftArm != null)
                armorStand.setLeftArmPose(f.leftArm);

            if (f.rightLeg != null)
                armorStand.setRightLegPose(f.rightLeg);

            if (f.rightArm != null)
                armorStand.setRightArmPose(f.rightArm);
        }
        currentFrame++;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public int getLength() {
        return length;
    }

    public Frame[] getFrames() {
        return frames;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    private Frame interpolate(int frameID) {
        Frame minFrame = null;
        for (int i = frameID; i >= 0; i--) {
            if (frames[i] != null) {
                minFrame = frames[i];
                break;
            }
        }
        Frame maxFrame = null;
        for (int i = frameID; i < frames.length; i++)
            if (frames[i] != null) {
                maxFrame = frames[i];
                break;
            }

        Frame res;

        if (maxFrame == null || minFrame == null) {
            if (maxFrame == null && minFrame != null) return minFrame;
            if (minFrame == null && maxFrame != null) return maxFrame;

            res = new Frame();
            res.frameID = frameID;
            return res;
        }
        res = new Frame();
        res.frameID = frameID;

        float Dmin = frameID - minFrame.frameID;
        float D = maxFrame.frameID - minFrame.frameID;
        float D0 = Dmin / D;

        res = minFrame.mult(1 - D0, frameID).add(maxFrame.mult(D0, frameID), frameID);

        return res;
    }

    public static final class Frame {
        int frameID;
        float x, y, z, r;
        EulerAngle middle;
        EulerAngle rightLeg;
        EulerAngle leftLeg;
        EulerAngle rightArm;
        EulerAngle leftArm;
        EulerAngle head;

        public Frame mult(float a, int frameID) {
            Frame f = new Frame();
            f.frameID = frameID;
            f.x = f.x * a;
            f.y = f.y * a;
            f.z = f.z * a;
            f.r = f.r * a;
            f.middle = new EulerAngle(middle.getX() * a, middle.getY() * a, middle.getZ() * a);
            f.rightLeg = new EulerAngle(rightLeg.getX() * a, rightLeg.getY() * a, rightLeg.getZ() * a);
            f.leftLeg = new EulerAngle(leftLeg.getX() * a, leftLeg.getY() * a, leftLeg.getZ() * a);
            f.rightArm = new EulerAngle(rightArm.getX() * a, rightArm.getY() * a, rightArm.getZ() * a);
            f.leftArm = new EulerAngle(leftArm.getX() * a, leftArm.getY() * a, leftArm.getZ() * a);
            f.head = new EulerAngle(head.getX() * a, head.getY() * a, head.getZ() * a);
            return f;
        }

        public Frame add(Frame a, int frameID) {
            Frame f = new Frame();
            f.frameID = frameID;
            f.x = f.x + a.x;
            f.y = f.y + a.y;
            f.z = f.z + a.z;
            f.r = f.r + a.r;
            f.middle = new EulerAngle(middle.getX() + a.middle.getX(), middle.getY() + a.middle.getY(), middle.getZ() + a.middle.getZ());
            f.rightLeg = new EulerAngle(rightLeg.getX() + a.rightLeg.getX(), rightLeg.getY() + a.rightLeg.getY(), rightLeg.getZ() + a.rightLeg.getZ());
            f.leftLeg = new EulerAngle(leftLeg.getX() + a.leftLeg.getX(), leftLeg.getY() + a.leftLeg.getY(), leftLeg.getZ() + a.leftLeg.getZ());
            f.rightArm = new EulerAngle(rightArm.getX() + a.rightArm.getX(), rightArm.getY() + a.rightArm.getY(), rightArm.getZ() + a.rightArm.getZ());
            f.leftArm = new EulerAngle(leftArm.getX() + a.leftArm.getX(), leftArm.getY() + a.leftArm.getY(), leftArm.getZ() + a.leftArm.getZ());
            f.head = new EulerAngle(head.getX() + a.head.getX(), head.getY() + a.head.getY(), head.getZ() + a.head.getZ());
            return f;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Frame frame = (Frame) o;
            return frameID == frame.frameID && Float.compare(x, frame.x) == 0 && Float.compare(y, frame.y) == 0 && Float.compare(z, frame.z) == 0 && Float.compare(r, frame.r) == 0 && Objects.equals(middle, frame.middle) && Objects.equals(rightLeg, frame.rightLeg) && Objects.equals(leftLeg, frame.leftLeg) && Objects.equals(rightArm, frame.rightArm) && Objects.equals(leftArm, frame.leftArm) && Objects.equals(head, frame.head);
        }

        @Override
        public int hashCode() {
            return Objects.hash(frameID, x, y, z, r, middle, rightLeg, leftLeg, rightArm, leftArm, head);
        }

        @Override
        public String toString() {
            return "Frame{" +
                    "frameID=" + frameID +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", r=" + r +
                    ", middle=" + middle +
                    ", rightLeg=" + rightLeg +
                    ", leftLeg=" + leftLeg +
                    ", rightArm=" + rightArm +
                    ", leftArm=" + leftArm +
                    ", head=" + head +
                    '}';
        }
    }

}