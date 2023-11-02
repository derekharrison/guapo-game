//
//  GameObjects.swift
//  SoloMission
//
//  Created by Derek Harrison on 04/10/2023.
//

import Foundation
import SpriteKit

class Bubbles {
    
    var bubbles : [SKSpriteNode]
    var bubcounter : Int
    var is_muted : Bool
    
    init() {
        self.bubbles = [SKSpriteNode]()
        self.bubcounter = 1
        self.is_muted = false
    }
    
    func add_bubble(image_id : String) {
        
        self.bubbles.append(SKSpriteNode(imageNamed: image_id))
    }
    
    func play_bubbles() {
        bubcounter = 1
        for x in bubbles {
            x.zPosition = -1
        }
    }
    
    func set_pos_bubbles(pos : CGPoint) {
        for x in bubbles {
            x.position = pos
        }
    }

    func pop_bubbles_api(pos : CGPoint, scene : SKScene, sound : [SKAction]) {
        
        if self.bubcounter == 1 {
            self.bubbles[0].position = pos
            self.bubbles[0].zPosition = 1000
            self.bubcounter += 1
            if self.is_muted == false {
                play_sound_api(scene : scene, sound : sound)
            }
            
        }
        else if self.bubcounter < 40 {
            self.bubbles[0].position.y += bubble_vel_y
            self.bubcounter += 1
        }
        else if bubcounter == 40 {
            self.bubbles[1].position = pos
            self.bubbles[1].zPosition = 1000
            self.bubcounter += 1
        }
        else if bubcounter < 80 {
            self.bubbles[0].position.y += bubble_vel_y
            self.bubbles[1].position.y += bubble_vel_y
            self.bubcounter += 1
        }
        else if self.bubcounter == 80 {
            self.bubbles[2].position = pos
            self.bubbles[2].zPosition = 1000
            self.bubcounter += 1
        }
        else if self.bubcounter < 240 {
            self.bubbles[0].position.y += bubble_vel_y
            self.bubbles[1].position.y += bubble_vel_y
            self.bubbles[2].position.y += bubble_vel_y
            self.bubcounter += 1
        }
        if self.bubcounter == 240 {
            play_bubbles()
        }
    }
}

class Player : GameObject {
    
    override init() { super.init() }
    
    init(images : [String], size : CGSize, z_pos : CGFloat) {
        super.init()
        
        for x in images {
            self.add_image(image: x)
        }
        
        self.set_pos(pos: CGPoint(x: -5000, y: 0))
        self.set_z_pos(z_pos: z_pos)
        self.set_size(size: size)
    }
    
    func add_image_hit(image : String, size : CGSize, z_pos : CGFloat) {
        let image_hit = SKSpriteNode(imageNamed: image)
        image_hit.size = size
        image_hit.zPosition = z_pos
        self.images_hit.append(image_hit)
    }
    
    func update_pos_api() {
        self.update_pos3()
    }
    
    func set_pos_api(pos : CGPoint) {
        self.set_pos(pos: pos)
        
        for x in images_hit {
            x.position = pos
        }
    }
}

class Bird : GameObject {
    
    override init() { super.init() }
    
    init(birds : [String], size : CGSize, z_pos : CGFloat) {
        super.init()
        
        for x in birds {
            self.add_image(image: x)
        }
        
        self.set_pos(pos: CGPoint(x: -5000, y: 0))
        self.set_z_pos(z_pos: z_pos)
        self.set_size(size: size)
    }
}

class JellyFish : GameObject {
    
    var counter : Int = 1
    
    init(jelly_fish : [String], size : CGSize, z_pos : CGFloat) {
        super.init()
        
        for x in jelly_fish {
            self.add_image(image: x)
        }
        
        self.set_pos(pos: CGPoint(x: -5000, y: 0))
        self.set_z_pos(z_pos: z_pos)
        self.set_size(size: size)
    }

    func update_pos_api() {
        
        if counter < 20 {
            for x in images {
                x.position.y += 2
            }
            counter += 1
        }
        else if counter < 40 {
            for x in images {
                x.position.y -= 2
            }
            counter += 1
        }
        else if counter == 40 {
            counter = 1
        }
    }
}


class Brownie : GameObject {
    
    override init() { super.init() }
    
    func update_pos_api(scene : SKScene) {
        
        self.reflect(scene : scene)
        self.update_pos(scene : scene, at_screen : 10)
        
        if hit {
            self.birdi_on_top(image_id: 1)
        }
        
        if !hit {
            self.birdi_on_top(image_id: 0)
        }
        
        self.appeared = self.images[0].position.x > 0 && self.images[0].position.x < scene.size.width
    }
}

class Frito : GameObject {
    override init() { super.init() }
    
    func update_pos_api(scene : SKScene) {
        
        self.reflect(scene : scene)
        self.update_pos_rev(scene : scene, at_screen : -15)
        
        if hit {
            self.birdi_on_top(image_id: 1)
        }
        
        if !hit {
            self.birdi_on_top(image_id: 0)
        }
        
        self.appeared = self.images[0].position.x > 0 && self.images[0].position.x < scene.size.width
    }
}

class Snack : GameObject {
    var points_snack : Int
    
    init(bite : String, points : Int, size : CGSize, z_pos : CGFloat) {
        self.points_snack = points
        super.init()
        self.add_image(image: bite)
        
        let factor = 1.0 - (self.images[0].size.height) / (self.height / 2)
        let pos_x = get_rand_num() * self.width * 2
        let pos_y = get_rand_num() * self.height / 2 * factor + self.height / 4 + 1/2 * (1 - factor) * self.height / 2
        
        self.set_pos(pos: CGPoint(x: pos_x, y: pos_y))
        self.set_z_pos(z_pos: z_pos)
        self.set_size(size: size)
    }
}

class Misty : GameObject {
    var top : Bool
    var counter1 = 1000
    var counter2 = 1000
    
    override init() {
        self.top = true
        super.init()
        self.height = 100000
        self.width = -100000
        self.set_pos(pos: CGPoint(x: -5000, y: 0))
    }
    
    func play_misty(bool : Bool) {
        self.counter1 = 1;
        self.counter2 = 1;
        
        self.top = bool
    }
    
    func set_vel_misty(vx : CGFloat, vy : CGFloat) {
        self.set_vel(vel_x: vx, vel_y: vy)
    }
    
    func set_pos_api(pos : CGPoint) {
        if !top {
            self.images[0].position = pos
            self.images[1].position = pos
        }
        else {
            self.images[2].position = pos
            self.images[3].position = pos
        }
    }
    
    func pop_misty(height : CGFloat, speed : CGFloat) {
        
        if !self.hit && top {
            self.birdi_on_top(image_id: 2)
        }
        
        if self.hit && top {
            self.birdi_on_top(image_id: 3)
        }
        
        if !self.hit && !top {
            self.birdi_on_top(image_id: 0)
        }
        
        if self.hit && !top {
            self.birdi_on_top(image_id: 1)
        }
        
        if counter1 < 60 {
            self.update_pos()
            counter1 += 1
        }
        
        else if counter1 == 60 {
            self.vel_y = -self.vel_y
            counter1 += 1
        }
        
        else if counter1 < 120 {
            self.update_pos()
            counter1 += 1
        }
    }
}

class BlowFish : GameObject {
    
    override init() {
        super.init()
    }
    
    func update_pos_api(scene : SKScene, at : Int) {
        
        if !self.hit {
            self.update_pos(scene: scene, at_screen : at)
        }
        if self.hit {
            self.update_pos_hit(scene: scene, at_screen: at)
        }
    }
}

class Button : GameObject {
    
    override init() {
        super.init()
    }
}

class Flag : GameObject {
    
    override init() {
        super.init()
    }
}
