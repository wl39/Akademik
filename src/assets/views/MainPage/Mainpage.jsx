import React from "react";
import { Link } from "react-router-dom";

import Button from '../../../components/Button/Button'
import Card from '../../../components/Card/Card'

import Akademik from '../../images/akademik.png'
import Step1 from '../../images/step1.png'
import Step2 from '../../images/step2.png'
import Step3 from '../../images/step3.png'

import BookOpended1 from '../../images/Book_opened-1.png'
import BookOpended2 from '../../images/Book_opened-2.png'
import BookEndBottomLeft from '../../images/Book-end_bottom-left.png'
import BookEndBottomLeft1 from '../../images/Book-end_bottom-left-1.png'
import BookEndBottomLeft2 from '../../images/Book-end_bottom-left-2.png'
import BookEndBottomRight from '../../images/Book-end_bottom-right.png'
import BookEndLeft from '../../images/Book-end_left.png'
import BookEndMiddle from '../../images/Book-end_middle.png'
import BookEndRight from '../../images/Book-end_right.png'

import MainPageEnd from '../../images/mainpage-end.png'

import styles from './MainPage.module.css';

let slogan = "Academic Articles.\nReimagined.";
let step1 = "Upload your files, fill out the\nmetadata and click submit. it's that\neasy.";
let step2 = "Leading academics in your field of study\nwill be hand-picked to check your\nsubmission – from your grammar to your\ncode.";
let step3 = "Your academic paper is now officially\npublished on akademik – members of\nthe scientific community and the\ngeneral public alike can marvel at\nyour work.";

const lstbutton = {
    width: "174px",
    height: "60px",
    margin: "auto 7px auto 0px",
    lineHeight: "60px",
    fontWeight: "bold",
    top: "-130px"
}



class MainPage extends React.Component {
    render() {
        return (
            <div className="Akademik" >
                <header className={styles.headerContainer}>
                    <div>
                        <img src={Akademik} alt="Akademik" style={{ marginTop: "48px" }} />


                        <div className={styles.slogan}>
                            {slogan}
                        </div>

                        <div className={styles.bookContainer}>
                            <img className={styles.book} src={BookEndBottomLeft} alt="bebl" style={{ top: "955px", zIndex: "2", transform: "translate(-100%)" }} />
                            <img className={styles.book} src={BookEndBottomLeft1} alt="bebl1" style={{ top: "1192px", zIndex: "2", transform: "translate(-157.2%)" }} />
                            <img className={styles.book} src={BookEndBottomLeft2} alt="bebl2" style={{ top: "1192px", zIndex: "2", transform: "translate(57.3%)" }} />

                            <img className={styles.book} src={BookEndBottomRight} alt="bebr" style={{ top: "955px", zIndex: "2", transform: "translate(0%)" }} />
                            <img className={styles.book} src={BookEndLeft} alt="bel" style={{ top: "59px", zIndex: "2", transform: "translate(-1266.1%)" }} />
                            <img className={styles.book} src={BookEndMiddle} alt="bem" style={{ top: "130px", zIndex: "2", transform: "translate(-47%)" }} />

                            <img className={styles.book} src={BookEndRight} alt="ber" style={{ top: "64px", zIndex: "2", transform: "translate(1145.5%)" }} />
                            <img className={styles.book} src={BookOpended1} alt="bo1" />
                            <img className={styles.book} src={BookOpended2} alt="bo2" style={{ top: "86px" }} />
                            <div className={styles.sliderContainer}>
                                <div className={styles.sliderHeader}>Some of our favorite publications.</div>
                                <div>
                                    <Card styleName="cardWhite round relative">
                                        <div className={styles.smallCard}>
                                            <div className={styles.cardHeader}>
                                                End-to-End Mobility for the Internet Using INLP
                                            </div>
                                            <div className={styles.cardBody}>
                                                As the use of mobile devices and methods of wireless connectivity continue to increase, seamless mobility becomes more desirable and important. The current IETF Mobile IP standard relies on additional network entities for mobility management, can have poor performance, and has seen little deployment in real networks. We present a host-based mobility solution with a true end-to-end architecture using the...
                                            </div>
                                        </div>
                                    </Card>
                                    <Card styleName="cardWhite round relative" inlineStyles={{margin: "0 20px"}}>
                                        <div className={styles.card}>
                                            <div className={styles.cardHeader}>
                                                End-to-End Privacy for Identity & Location with IP
                                            </div>
                                            <div className={styles.cardBody}>
                                                The direct and flexible use of any network connectivity that is available within an urban scenario is essential for the successful operation of ubiquitous systems. We demonstrate seamless communication across different networks without the use of middleware, proxies, tunnels, or address translation, with minimal (near-zero) packet loss to communication flows as handoff occurs between networks. Our solution does not require any new functions in existing networks, will work on existing infrastructure, and does not require applications to be re-designed or re-engineered. Our solution requires only modifications to the end-systems involved in communication, so can be deployed incrementally only for those end-systems that require the functionality. We describe our approach and its design, based on the use of the Identifier-Locator Network Protocol (ILNP), which can be realised directly on IPv6. We demonstrate the efficacy of our solution with testbed experiments based on modifications to the Linux kernel v4.9 LTS, operating directly overIPv6, and using unmodified binary applications utilising directly the standard socket(2) POSIX.1-2008 API, and standard C library calls. As our approach is ‘end-to-end’, we also describe how to maintain packet-level secrecy and identity privacy for the communication flow as part of our approach.
                                            </div>
                                        </div>
                                    </Card>
                                    <Card styleName="cardWhite round relative">
                                        <div className={styles.smallCard}>
                                            <div className={styles.cardHeader}>
                                                End-to-End Networking with INLP in Linux
                                            </div>
                                            <div className={styles.cardBody}>
                                                The Identifier-Locator Network Protocol (ILNP) is defined as an Experimental Internet Protocol by the Internet Research Task Force (IRTF) in RFCs 6740-6748. At the heart of the ILNP architecture is a radical approach to addressing for the Internet: the deprecation of IP addresses, to be replaced by the use of node Identifiers and network Locators. The key benefits of ILNP are to allow many functions...
                                            </div>
                                        </div>
                                    </Card>
                                </div>
                            </div>
                            <div className={styles.marketingHookContainer}>
                                <p className={styles.marketingHook}>Countless academics get their work peer reviewed and published on akademik – one of the most trusted and respected academic journals in the world.</p>
                            </div>
                        </div>

                        <div className={styles.steps}>
                            <div className={styles.stepTitle}>How it works</div>

                            <div className={styles.step1}>
                                <div className={styles.step1Container}>
                                    <div className={styles.step1TextContainer}>
                                        <div className={styles.step1Header}>Submit your academic paper.</div>
                                        <div className={styles.step1Text}>{step1}</div>
                                    </div>
                                    <img className={styles.step1Image} src={Step1} alt="step1" />
                                </div>
                            </div>
                            <div className={styles.step2}>
                                <div className={styles.step2Container}>
                                    <div className={styles.step2TextContainer}>
                                        <div className={styles.step2Header}>Your work is peer-reviewed.</div>
                                        <div className={styles.step2Text}>{step2}</div>
                                    </div>
                                    <img className={styles.step2Image} src={Step2} alt="step2" />
                                </div>
                            </div>
                            <div className={styles.step3}>
                                <div className={styles.step1Container}>
                                    <div className={styles.step1TextContainer}>
                                        <div className={styles.step1Header}>You're published!</div>
                                        <div className={styles.step1Text}>{step3}</div>
                                    </div>
                                    <img className={styles.step1Image} src={Step3} alt="step3" />
                                </div>
                            </div>
                        </div>

                        <div className={styles.end}>
                            <img src={MainPageEnd} alt="mainpage-end" />
                            <Link to="/register">
                                <Button text="Let's Do It!"
                                    inlineStyle={lstbutton}
                                    styleName="white"
                                />
                            </Link>
                        </div>
                    </div>
                </header>
                {/* <img src={heroBackground} alt="hero" className="hero" /> */}
            </div>
        );
    }
}

export default MainPage;
