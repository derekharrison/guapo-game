//
//  GameLevel4.swift
//  SoloMission
//
//  Created by Derek Harrison on 25/09/2023.
//

import Foundation

import SpriteKit
import GameplayKit

class GameLevel4: SKScene {
    
    var base = GameLevel()
    var num_backgrounds : Int = NUM_BACKGROUNDS_LEVEL_4
    
    override func update(_ currentTime: TimeInterval) {
        base.update_ocean(scene: self)
    }
    
    override func didMove(to view: SKView) {
        base.didMove_ocean(scene: self, id : LEVEL_ID_4)

        base.add_bubbles(bubbles_char: base.player.bubbles, bubble_image: BUBBLE_IMAGE_STR)

        var images = [String]()
        images.append(FRITO_OCEAN_1)
        images.append(FRITO_OCEAN_2)
        base.add_bubbles(bubbles_char: base.frito.bubbles, bubble_image: BUBBLE_IMAGE_STR)
        
        for x in base.frito.bubbles.bubbles {
            x.removeFromParent()
            self.addChild(x)
        }
        
        base.init_images_frito(images: images, height: self.size.height, width: self.size.width)
        
        var images_brownie = [String]()
        images_brownie.append(BROWNIE_OCEAN_1)
        images_brownie.append(BROWNIE_OCEAN_2)
        base.add_bubbles(bubbles_char: base.brownie.bubbles, bubble_image: BUBBLE_IMAGE_STR)
        
        for x in base.brownie.bubbles.bubbles {
            x.removeFromParent()
            self.addChild(x)
        }
        
        base.init_images_brownie(images: images_brownie, height: self.size.height, width: self.size.width)
        
        var images_misty = [String]()
        images_misty.append(MISTY_OCEAN_1)
        images_misty.append(MISTY_OCEAN_2)
        images_misty.append(MISTY_OCEAN_3)
        images_misty.append(MISTY_OCEAN_4)
        base.add_bubbles(bubbles_char: base.misty.bubbles, bubble_image: BUBBLE_IMAGE_STR)
        
        for x in base.misty.bubbles.bubbles {
            x.removeFromParent()
            self.addChild(x)
        }
        
        base.init_images_misty(images: images_misty, height: self.size.height, width: self.size.width)
        
        base.init_background(scene: self, num_backgrounds: num_backgrounds, string1: BACKGROUND_STR_LEVEL_4)
        
        var jelly_images = [String]()
        
        jelly_images.append(JELLY_IMAGE_1)
        jelly_images.append(JELLY_IMAGE_2)
        jelly_images.append(JELLY_IMAGE_3)
        
        base.add_jellyfish(images : jelly_images)
        
        if base.playing {
            base.get_state()
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        base.touchesBegan(touches, with: event)
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        base.touchesMoved(touches, with: event)
    }
}
