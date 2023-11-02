//
//  GlobalFunctions.swift
//  SoloMission
//
//  Created by Derek Harrison on 04/10/2023.
//

import Foundation
import SpriteKit

func save_snack(object : Snack, prefix : String) {
    let defaults = UserDefaults()
    
    defaults.set(object.z_pos, forKey: prefix + "z_pos")
    defaults.set(object.vel_x, forKey: prefix + "vel_x")
    defaults.set(object.vel_y, forKey: prefix + "vel_y")
    defaults.set(object.play_sound, forKey: prefix + "play_sound")
    defaults.set(object.bird_counter, forKey: prefix + "bird_counter")
    defaults.set(object.bird_counter_hit, forKey: prefix + "bird_counter_hit")
    defaults.set(object.bird_id, forKey: prefix + "bird_id")
    defaults.set(object.bird_id_hit, forKey: prefix + "bird_id_hit")
    defaults.set(object.width, forKey: prefix + "width")
    defaults.set(object.height, forKey: prefix + "height")
    defaults.set(object.hit, forKey: prefix + "hit")
    defaults.set(object.appeared, forKey: prefix + "appeared")
    defaults.set(object.pos_x, forKey: prefix + "pos_x")
    defaults.set(object.pos_y, forKey: prefix + "pos_y")
    
    defaults.set(object.bubbles.bubcounter, forKey: prefix + "bubbles.bubcounter")
    defaults.set(object.bubbles.is_muted, forKey: prefix + "bubbles.is_muted")
    defaults.set(object.points_snack, forKey: prefix + "points_snack")
}

func save_snacks(snacks : [Snack], prefix : String) {
    let defaults = UserDefaults()
    
    var counter = 0
    
    for snack in snacks {
        save_snack(object: snack, prefix: prefix + String(counter))
        counter += 1
    }
    defaults.set(snacks.count, forKey: prefix + "num_snacks")
    
}

func get_snacks(snacks : inout [Snack], prefix : String) {
    let defaults = UserDefaults()
    let num_snacks = defaults.integer(forKey: prefix + "num_snacks")
    for counter in 0..<num_snacks {
        get_snack(object: &snacks[counter], prefix: prefix + String(counter))
    }
}

func get_snack(object : inout Snack, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.set_pos(pos: CGPoint(x: object.pos_x, y: object.pos_y))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
    object.points_snack = defaults.integer(forKey: prefix + "points_snack")
}

func save_misty(object : Misty, prefix : String) {

    let defaults = UserDefaults()
    
    defaults.set(object.z_pos, forKey: prefix + "z_pos")
    defaults.set(object.vel_x, forKey: prefix + "vel_x")
    defaults.set(object.vel_y, forKey: prefix + "vel_y")
    defaults.set(object.play_sound, forKey: prefix + "play_sound")
    defaults.set(object.bird_counter, forKey: prefix + "bird_counter")
    defaults.set(object.bird_counter_hit, forKey: prefix + "bird_counter_hit")
    defaults.set(object.bird_id, forKey: prefix + "bird_id")
    defaults.set(object.bird_id_hit, forKey: prefix + "bird_id_hit")
    defaults.set(object.width, forKey: prefix + "width")
    defaults.set(object.height, forKey: prefix + "height")
    defaults.set(object.hit, forKey: prefix + "hit")
    defaults.set(object.appeared, forKey: prefix + "appeared")
    defaults.set(object.pos_x, forKey: prefix + "pos_x")
    defaults.set(object.pos_y, forKey: prefix + "pos_y")
    
    defaults.set(object.bubbles.bubcounter, forKey: prefix + "bubbles.bubcounter")
    defaults.set(object.bubbles.is_muted, forKey: prefix + "bubbles.is_muted")
    
    defaults.set(object.top, forKey: prefix + "top")
    defaults.set(object.counter1, forKey: prefix + "counter1")
    defaults.set(object.counter2, forKey: prefix + "counter2")
}

func get_misty_object(object : inout Misty, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
    
    object.top = defaults.bool(forKey: prefix + "top")
    object.counter1 = defaults.integer(forKey: prefix + "counter1")
    object.counter2 = defaults.integer(forKey: prefix + "counter2")
    
    object.set_pos_api(pos: CGPoint(x: object.pos_x, y: object.pos_y))
}

func save_object(object : GameObject, prefix : String) {
    let defaults = UserDefaults()
    
    defaults.set(object.z_pos, forKey: prefix + "z_pos")
    defaults.set(object.vel_x, forKey: prefix + "vel_x")
    defaults.set(object.vel_y, forKey: prefix + "vel_y")
    defaults.set(object.play_sound, forKey: prefix + "play_sound")
    defaults.set(object.bird_counter, forKey: prefix + "bird_counter")
    defaults.set(object.bird_counter_hit, forKey: prefix + "bird_counter_hit")
    defaults.set(object.bird_id, forKey: prefix + "bird_id")
    defaults.set(object.bird_id_hit, forKey: prefix + "bird_id_hit")
    defaults.set(object.width, forKey: prefix + "width")
    defaults.set(object.height, forKey: prefix + "height")
    defaults.set(object.hit, forKey: prefix + "hit")
    defaults.set(object.appeared, forKey: prefix + "appeared")
    defaults.set(object.pos_x, forKey: prefix + "pos_x")
    defaults.set(object.pos_y, forKey: prefix + "pos_y")
    
    defaults.set(object.bubbles.bubcounter, forKey: prefix + "bubbles.bubcounter")
    defaults.set(object.bubbles.is_muted, forKey: prefix + "bubbles.is_muted")
}

func get_object(object : inout GameObject, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.set_pos(pos: CGPoint(x: object.pos_x, y: object.pos_y))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
}

func get_blowfish(object : inout BlowFish, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.set_pos(pos: CGPoint(x: object.pos_x, y: object.pos_y))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
}

func get_brownie_object(object : inout Brownie, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.set_pos(pos: CGPoint(x: object.pos_x, y: object.pos_y))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
}

func get_frito_object(object : inout Frito, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.set_pos(pos: CGPoint(x: object.pos_x, y: object.pos_y))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
}

func get_player_object(object : inout Player, prefix : String) {
    let defaults = UserDefaults()
    
    object.z_pos = CGFloat(defaults.float(forKey: prefix + "z_pos"))
    object.vel_x = CGFloat(defaults.float(forKey: prefix + "vel_x"))
    object.vel_y = CGFloat(defaults.float(forKey: prefix + "vel_y"))
    object.play_sound = defaults.bool(forKey: prefix + "play_sound")
    object.bird_counter = defaults.integer(forKey: prefix + "bird_counter")
    object.bird_counter_hit = defaults.integer(forKey: prefix + "bird_counter_hit")
    object.bird_id = defaults.integer(forKey: prefix + "bird_id")
    object.bird_id_hit = defaults.integer(forKey: prefix + "bird_id_hit")
    object.width = CGFloat(defaults.float(forKey: prefix + "width"))
    object.height = CGFloat(defaults.float(forKey: prefix + "height"))
    object.hit = defaults.bool(forKey: prefix + "hit")
    object.appeared = defaults.bool(forKey: prefix + "appeared")
    object.pos_x = CGFloat(defaults.float(forKey: prefix + "pos_x"))
    object.pos_y = CGFloat(defaults.float(forKey: prefix + "pos_y"))
    
    object.set_pos(pos: CGPoint(x: object.pos_x, y: object.pos_y))
    
    object.bubbles.bubcounter = defaults.integer(forKey: prefix + "bubbles.bubcounter")
    object.bubbles.is_muted = defaults.bool(forKey: prefix + "bubbles.is_muted")
}

func start_scene(scene : SKScene, start : inout Bool, GameLevel : SKScene) {
    if start {
        let sceneToMoveTo = GameLevel
        sceneToMoveTo.scaleMode = scene.scaleMode
        let myTransition = SKTransition.fade(withDuration: 3.0)
        scene.view!.presentScene(sceneToMoveTo, transition: myTransition)
        
        start = false
    }
}

func play_sound_api(scene : SKScene, sound : [SKAction]) {
    let bubble_sound = SKSpriteNode()
    scene.addChild(bubble_sound)

    let bubble_sound_biteSequence = SKAction.sequence(sound)
    bubble_sound.run(bubble_sound_biteSequence)
}

func get_rand_num() -> CGFloat {
    CGFloat(Float(arc4random()) / Float(UINT32_MAX))
}

func doOverlap(l1: CGPoint ,r1: CGPoint, l2: CGPoint, r2: CGPoint) -> Bool {
 
    // At least one of the rectangles is a line
    if (l1.x == r1.x || l1.y == r1.y || l2.x == r2.x
        || l2.y == r2.y) {
        return false;
    }
 
    // If one rectangle is on left side of other
    if (l1.x >= r2.x || l2.x >= r1.x) {
        return false;
    }
 
    // If one rectangle is above other
    if (r1.y >= l2.y || r2.y >= l1.y) {
        return false;
    }
 
    return true
}

func check_collision(bird : GameObject, player : Player, den : CGFloat) -> Bool {
    
    var l1 = bird.images[0].position
    var r1 = l1
    l1.x -= bird.get_size().width / den
    l1.y += bird.get_size().height / den
    r1.x += bird.get_size().width / den
    r1.y -= bird.get_size().height / den
    var l2 = player.images[0].position
    var r2 = player.images[0].position
    l2.x -= player.images[0].size.width / den
    l2.y += player.images[0].size.height / den
    r2.x += player.images[0].size.width / den
    r2.y -= player.images[0].size.height / den
                    
    return doOverlap(l1: l1 ,r1: r1, l2: l2, r2: r2)
}
