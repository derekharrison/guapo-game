//
//  MainMenuScene.swift
//  SoloMission
//
//  Created by Derek Harrison on 29/07/2021.
//

import Foundation
import SpriteKit

let unlock_level_points = LEVEL_UNLOCK_GUARD

class MainMenuScene: SKScene {

    let levels_text = SKLabelNode(fontNamed: GAME_FONT)
    let high_score_text = SKLabelNode(fontNamed: GAME_FONT)
    
    let level_1_np = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_NOTPRESSED_1)
    let level_1_p = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_PRESSED_1)
    let level_2_np = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_NOTPRESSED_2)
    let level_2_p = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_PRESSED_2)
    let level_3_np = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_NOTPRESSED_3)
    let level_3_p = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_PRESSED_3)
    let level_4_np = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_NOTPRESSED_4)
    let level_4_p = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_PRESSED_4)
    let level_5_np = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_NOTPRESSED_5)
    let level_5_p = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_PRESSED_5)
    
    let level_2_grey = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_GRAY_2)
    let level_3_grey = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_GRAY_3)
    let level_4_grey = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_GRAY_4)
    let level_5_grey = SKSpriteNode(imageNamed: MAIN_MENU_BUTTON_GRAY_5)
    
    let level_1_score = SKLabelNode(fontNamed: GAME_FONT)
    let level_2_score = SKLabelNode(fontNamed: GAME_FONT)
    let level_3_score = SKLabelNode(fontNamed: GAME_FONT)
    let level_4_score = SKLabelNode(fontNamed: GAME_FONT)
    let level_5_score = SKLabelNode(fontNamed: GAME_FONT)
    
    var label = SKLabelNode(fontNamed: LABEL_FONT)

    let volume_on = SKSpriteNode(imageNamed: SOUND_ON_IMAGE_STR)
    let volume_off = SKSpriteNode(imageNamed: SOUND_OFF_IMAGE_STR)
    
    var muted = false

    var start_new_scene_touched = false
    var start_new_scene = false
    
    var start_level_1_touched = false
    var start_level_1 = false
    var start_level_1_scene = false
    var start_level_2_touched = false
    var start_level_2 = false
    var start_level_2_scene = false
    var start_level_3_touched = false
    var start_level_3 = false
    var start_level_3_scene = false
    var start_level_4_touched = false
    var start_level_4 = false
    var start_level_4_scene = false
    var start_level_5 = false
    var start_level_5_scene = false
    var start_level_5_touched = false
    
    var highScoreNumberLevel1 = 0
    var highScoreNumberLevel2 = 0
    var highScoreNumberLevel3 = 0
    var highScoreNumberLevel4 = 0
    var highScoreNumberLevel5 = 0
    
    override func didMove(to view: SKView) {
        
        start_new_scene = false
        start_new_scene_touched = false
        
        let width = self.size.width / 16
        let height = self.size.height / 20
        
        volume_off.setScale(1)
        volume_off.size = CGSize(width: width, height: height)
        volume_off.position = CGPoint(x: self.size.width - self.size.width/12, y: self.size.height/2 + self.size.height*1.9/5/2)
        volume_off.zPosition = 2
        
        volume_on.setScale(1)
        volume_on.size = CGSize(width: width, height: height)
        volume_on.position = volume_off.position
        volume_on.zPosition = -1
        
        let defaults = UserDefaults()
        muted = defaults.bool(forKey: GAME_MUTED)
        if muted {
            volume_off.zPosition = 2
            volume_on.zPosition = -1
        }
        else {
            volume_off.zPosition = -1
            volume_on.zPosition = 2
        }
        
        self.addChild(volume_off)
        self.addChild(volume_on)
        let background = SKSpriteNode(imageNamed: BACKGROUND_START_SCREEN)
        
        let widthb = background.size.width
        let heightb = self.size.height/2
        
        background.size = CGSize(width: widthb, height: heightb)
        
        background.position = CGPoint(x: self.size.width / 2, y: self.size.height / 2)
        background.zPosition = 0
        self.addChild(background)
        
        levels_text.text = LEVELS_STR
        levels_text.fontSize = 80
        levels_text.fontColor = SKColor.gray
        levels_text.position = CGPoint(x: self.size.width/2 - self.size.width/8, y: self.size.height/2 * 0.85 + self.size.height/4)
        levels_text.zPosition = 1
        self.addChild(levels_text)
        
        high_score_text.text = HIGHSCORE_STR
        high_score_text.fontSize = 80
        high_score_text.fontColor = SKColor.gray
        high_score_text.position = CGPoint(x: self.size.width / 2 + self.size.width / 8, y: self.size.height / 2 * 0.85 + self.size.height / 4)
        high_score_text.zPosition = 1
        self.addChild(high_score_text)
        
        highScoreNumberLevel1 = defaults.integer(forKey: HIGH_SCORE_ID_1)
        highScoreNumberLevel2 = defaults.integer(forKey: HIGH_SCORE_ID_2)
        highScoreNumberLevel3 = defaults.integer(forKey: HIGH_SCORE_ID_3)
        highScoreNumberLevel4 = defaults.integer(forKey: HIGH_SCORE_ID_4)
        highScoreNumberLevel5 = defaults.integer(forKey: HIGH_SCORE_ID_5)

        let width_button = self.size.width / 5
        let height_button = self.size.height / 16
        
        level_1_np.size = CGSize(width: width_button, height: height_button)
        level_1_np.position = CGPoint(x: self.size.width / 2 - self.size.width / 8, y: self.size.height / 2 * 0.73 + self.size.height / 4)
        level_1_np.zPosition = 2
        self.addChild(level_1_np)
        
        level_1_p.size = CGSize(width: width_button, height: height_button)
        level_1_p.position = CGPoint(x: self.size.width/2 - self.size.width/8, y: self.size.height/2 * 0.73 + self.size.height/4)
        level_1_p.zPosition = -1
        self.addChild(level_1_p)
        
        level_1_score.text = String(highScoreNumberLevel1)
        level_1_score.fontSize = 70
        level_1_score.fontColor = SKColor.gray
        level_1_score.position = CGPoint(x: self.size.width/2 + self.size.width/8, y: self.size.height/2 * 0.70 + self.size.height/4)
        level_1_score.zPosition = 1

        level_1_score.zPosition = 1
        self.addChild(level_1_score)
 
        place_level_button(score_prev_level : highScoreNumberLevel1, fac : 0.58, level_np : level_2_np, level_p : level_2_p, level_gray : level_2_grey, high_score : highScoreNumberLevel2, score_label : level_2_score)
        
        place_level_button(score_prev_level : highScoreNumberLevel2, fac : 0.43, level_np : level_3_np, level_p : level_3_p, level_gray : level_3_grey, high_score : highScoreNumberLevel3, score_label : level_3_score)
        
        place_level_button(score_prev_level : highScoreNumberLevel3, fac : 0.28, level_np : level_4_np, level_p : level_4_p, level_gray : level_4_grey, high_score : highScoreNumberLevel4, score_label : level_4_score)
        
        place_level_button(score_prev_level : highScoreNumberLevel3, fac : 0.13, level_np : level_5_np, level_p : level_5_p, level_gray : level_5_grey, high_score : highScoreNumberLevel5, score_label : level_5_score)
    }
    
    //Force the mute button to change by running update()
    override func update(_ currentTime: TimeInterval) {
        if muted {
            volume_off.zPosition = 2
            volume_on.zPosition = -1
        }
        else {
            volume_off.zPosition = -1
            volume_on.zPosition = 2
        }
        
        start_scene(scene: self, start : &start_level_1_scene, GameLevel : GameLevel1(size: self.size))
        start_scene(scene: self, start : &start_level_2_scene, GameLevel : GameLevel2(size: self.size))
        start_scene(scene: self, start : &start_level_3_scene, GameLevel : GameLevel3(size: self.size))
        start_scene(scene: self, start : &start_level_4_scene, GameLevel : GameLevel4(size: self.size))
        start_scene(scene: self, start : &start_level_5_scene, GameLevel : GameLevel5(size: self.size))
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        for touch: AnyObject in touches {
            
            let pointOfTouch = touch.location(in: self)
            
            let position = CGPoint(x: self.size.width - 2 * self.size.width / 12, y: self.size.height / 2 + self.size.height * 1.5 / 10)
            
            if pointOfTouch.x > position.x  && pointOfTouch.y > position.y && muted == false {
                volume_on.zPosition = 2
                volume_off.zPosition = -1
                muted = true

                let defaults = UserDefaults()
                defaults.set(muted, forKey: GAME_MUTED)
            }
            else if pointOfTouch.x > position.x  && pointOfTouch.y > position.y && muted == true {
                volume_on.zPosition = -1
                volume_off.zPosition = 2
                muted = false
                
                let defaults = UserDefaults()
                defaults.set(muted, forKey: GAME_MUTED)
            }
            else {
                
                touched_button(pointOfTouch: pointOfTouch, level_np : level_1_np, level_p : level_1_p, start_level_touched : &start_level_1_touched)
                touched_button(pointOfTouch: pointOfTouch, level_np : level_2_np, level_p : level_2_p, start_level_touched : &start_level_2_touched)
                touched_button(pointOfTouch: pointOfTouch, level_np : level_3_np, level_p : level_3_p, start_level_touched : &start_level_3_touched)
                touched_button(pointOfTouch: pointOfTouch, level_np : level_4_np, level_p : level_4_p, start_level_touched : &start_level_4_touched)
                touched_button(pointOfTouch: pointOfTouch, level_np : level_5_np, level_p : level_5_p, start_level_touched : &start_level_5_touched)
            }
        }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        if start_new_scene_touched {
            start_new_scene = true
        }
        
        if start_level_1_touched {
            start_level_1 = true
            start_level_1_scene = true
        }
        if start_level_2_touched {
            start_level_2 = true
            start_level_2_scene = true
        }
        if start_level_3_touched {
            start_level_3 = true
            start_level_3_scene = true
        }
        if start_level_4_touched {
            start_level_4 = true
            start_level_4_scene = true
        }
        if start_level_5_touched {
            start_level_5 = true
            start_level_5_scene = true
        }
    }
    
    func touched_button(pointOfTouch : CGPoint, level_np : SKSpriteNode, level_p : SKSpriteNode, start_level_touched : inout Bool) {
        let touched_button_x = pointOfTouch.x > level_np.position.x - level_np.size.width / 2 && pointOfTouch.x < level_np.position.x + level_np.size.width / 2
        
        let touched_button_y = pointOfTouch.y > level_np.position.y - level_np.size.height / 2 && pointOfTouch.y < level_np.position.y + level_np.size.height / 2
        
        if touched_button_x && touched_button_y {
            level_np.zPosition = -1
            level_p.zPosition = 2
            start_level_touched = true
        }
    }
    
    func place_level_button(score_prev_level : Int, fac : CGFloat, level_np : SKSpriteNode, level_p : SKSpriteNode, level_gray : SKSpriteNode, high_score : Int, score_label : SKLabelNode) {
        
        let width_button = self.size.width / 5
        let height_button = self.size.height / 16
        
        if score_prev_level >= unlock_level_points {
            level_np.size = CGSize(width: width_button, height: height_button)
            level_np.position = CGPoint(x: self.size.width / 2 - self.size.width / 8, y: self.size.height / 2 * fac + self.size.height / 4)
            level_np.zPosition = 2
            self.addChild(level_np)
            
            level_p.size = CGSize(width: width_button, height: height_button)
            level_p.position = CGPoint(x: self.size.width / 2 - self.size.width / 8, y: self.size.height / 2 * fac + self.size.height / 4)
            level_p.zPosition = -1
            self.addChild(level_p)
        }
        else {
            level_np.size = CGSize(width: width_button, height: height_button)
            level_np.position = CGPoint(x: self.size.width / 2 - self.size.width / 8, y: self.size.height / 2 * fac + self.size.height / 4)
            level_np.zPosition = -1
            self.addChild(level_np)
            
            level_gray.size = CGSize(width: width_button, height: height_button)
            level_gray.position = CGPoint(x: self.size.width / 2 - self.size.width / 8, y: self.size.height / 2 * fac + self.size.height / 4)
            level_gray.zPosition = 2
            self.addChild(level_gray)
            
            level_p.size = CGSize(width: width_button, height: height_button)
            level_p.position = CGPoint(x: self.size.width / 2 - self.size.width / 8, y: self.size.height / 2 * fac + self.size.height / 4)
            level_p.zPosition = -1
            self.addChild(level_p)
        }
        
        score_label.text = String(high_score)
        score_label.fontSize = 70
        score_label.fontColor = SKColor.gray
        score_label.position = CGPoint(x: self.size.width / 2 + self.size.width / 8, y: self.size.height / 2 * (fac - 0.03) + self.size.height / 4)
        score_label.zPosition = 1
        self.addChild(score_label)
    }
}

